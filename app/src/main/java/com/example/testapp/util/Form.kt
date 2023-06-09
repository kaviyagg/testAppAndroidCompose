package com.example.testapp.util


import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class FormBuilder(var formFields: Map<String, FormField<Any>>) {
    private var _form = MutableStateFlow(Form(formFields) { updatedField ->
        updateField(updatedField)
    })

    private fun updateField(updatedField: Map<String, FormField<Any>>) {
        _form.update { form ->
            Form(form.fields + updatedField) { result ->
                updateField(result)
            }
        }
    }

    val form: StateFlow<Form> = _form.asStateFlow()

    fun getAllValues(): Map<String, Any> {
        return form.value.getAllValues()
    }

    fun getForm(): Form {
        return form.value
    }

}

data class Form(
    var fields: Map<String, FormField<Any>>,
    val updateCallback: (result: Map<String, FormField<Any>>) -> Unit
) {

    val isFormValid: Boolean
        get() = isValidForm()
    val isFormTouched: Boolean
        get() = checkFormTouched()
    val isFormDirty: Boolean
        get() = fields.any { it.value.isDirty }

    init {
        // Validate Form
        assignForm()
        validateForm(skipUpdate = true)
    }

    private fun assignForm() {
        fields.forEach {
            it.value.setParentValue(this)
        }
    }

    fun markAsPristine() {
        fields.forEach {
            var updatedField = it.value
            updatedField = updatedField.copy(isTouched = false, isDirty = false)
            val updatedFields = fields + (it.key to updatedField)
            validate(it.key, updatedFields)
        }
        isValidForm()
    }

    fun update(fieldName: String, value: Any, checkValid: Boolean = true) {
        if (fields.containsKey(fieldName)) {
            Log.e("VALUE", value.toString())
            var updatedField = fields[fieldName]?.copy(value = value)
            if (checkValid) {
                updatedField = updatedField?.copy(isTouched = true, isDirty = true)
            }
            val updatedFields = fields + (fieldName to updatedField!!)
            validate(fieldName, updatedFields)
            isValidForm()
        }
    }

    fun touched(fieldName: String) {
        if (fields.containsKey(fieldName)) {
            val updatedField = fields[fieldName]?.copy(isTouched = true)
            val updatedFields = fields + (fieldName to updatedField!!)
            validate(fieldName, updatedFields)
            isValidForm()
        }
    }

    fun patch(
        updatedFields: Map<String, Any>, checkValid: Boolean = false
    ) {
        updatedFields.map {
            update(it.key, it.value, checkValid)
        }
    }

    private fun validate(
        fieldName: String, updatedFields: Map<String, FormField<Any>>, skipUpdate: Boolean = false
    ) {
        val validatedField = updatedFields[fieldName]

        var isValid = true
        var error: String? = null

        for (validation in validatedField?.validations ?: emptyList()) {
            val errorMessage = validation(validatedField!!)
            if (errorMessage != null) {
                isValid = false
                error = validatedField.messages[errorMessage] ?: "Invalid input"
                break
            }
        }


        val updatedField = validatedField?.copy(isValid = isValid, error = error)
        val newFields = updatedFields + (fieldName to updatedField!!)
        fields = newFields
        if (!skipUpdate) {
            updateCallback(newFields)
        }
    }

    private fun isValidForm(): Boolean {
        return fields.all { it.value.isValid }
    }

    private fun checkFormTouched(): Boolean {
        return fields.any { it.value.isTouched }
    }

    private fun validateForm(skipUpdate: Boolean = false) {
        fields.forEach { validate(it.key, fields, skipUpdate = skipUpdate) }
    }

    fun <T> getValue(fieldName: String): T? {
        return fields[fieldName]?.value as? T
    }

    fun getField(fieldName: String): FormField<Any> {
        return fields.getValue(fieldName)
    }

    fun getAllValues(): Map<String, Any> {
        return fields.mapValues { it.value.value }
    }

    fun resetForm(): Map<String, Any> {
        return fields.mapValues { it.value.value }
    }
}

data class FormField<T>(
    val value: T,
    val validations: List<(field: FormField<T>) -> String?> = emptyList(),
    val messages: Map<String, String> = mapOf(),
    val isValid: Boolean = false,
    val isDirty: Boolean = false,
    val isTouched: Boolean = false,
    val error: String? = null,
) {

    var parent: Form? = null

    val errorMessage: String?
        get() = if (showError) error else null

    val showError: Boolean
        get() = (!isValid && isTouched)

    fun setParentValue(form: Form) {
        parent = form
    }

}