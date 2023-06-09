package com.example.testapp.Model.api

data class Account(
    val activityLevel: String,
    val dob: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val goalType: String,
    val goalWeight: Int,
    val height: Int,
    val id: String,
    val initialWeight: Int,
    val isWeightlessOn: Boolean,
    val lastName: String,
    val preferredInputMethod: Any,
    val shouldSendEntryNotifications: Boolean,
    val shouldSendWeightInEntryNotifications: Boolean,
    val weightUnit: String,
    val weightlessBodyFat: Any,
    val weightlessMuscle: Any,
    val weightlessTimestamp: Any,
    val weightlessWeight: Any,
    val zipcode: String
)