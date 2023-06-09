package com.example.testapp.util

object ValidationType {
    const val MATCH_PASSWORD = "matchPassword"
    const val NOT_SAME = "notSame"
    const val REQUIRED = "required"
    const val EMAIL = "email"
    const val MIN_LENGTH = "min_length"
    const val MAX_LENGTH = "max_length"
    const val PATTERN = "pattern"
}

object FormValidations {

    fun required(): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        if (value.toString().isEmpty()) {
            ValidationType.REQUIRED
        } else {
            null
        }
    }

    fun minLength(length: Int): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        if (value != null && value.toString().length < length) {
            ValidationType.MIN_LENGTH
        } else {
            null
        }
    }

    fun maxLength(length: Int): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        if (value != null && value.toString().length > length) {
            ValidationType.MAX_LENGTH
        } else {
            null
        }
    }

    fun email(): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        if (value != null && !android.util.Patterns.EMAIL_ADDRESS.matcher(value.toString())
                .matches()
        ) {
            ValidationType.EMAIL
        } else {
            null
        }
    }

    fun pattern(pattern: String): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        if (value != null && !pattern.toRegex().matches(value.toString())) {
            ValidationType.PATTERN
        } else {
            null
        }
    }

    fun matchPassword(fieldName: String): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        val form = formField.parent

        if (value.toString()
                .isNotEmpty() && (form != null && form.getValue<Any>(fieldName) != value)
        ) {
            ValidationType.MATCH_PASSWORD
        } else {
            null
        }
    }

    fun notSame(fieldName: String): (formField: FormField<Any>) -> String? = { formField ->
        val value = formField.value
        val form = formField.parent

        if (value.toString()
                .isNotEmpty() && (form != null && form.getValue<Any>(fieldName) == value)
        ) {
            ValidationType.NOT_SAME
        } else {
            null
        }
    }
}