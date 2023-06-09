package com.example.testapp.config

object AppLang {


    object LoginPage {
        const val InvalidLogin =
            "Login Error \n" + "Your email or password is incorrect. Please try again"
        const val LoggingIn = "Logging in"
        const val ForgotYourPassword = "Forgot your password?"

        const val LoginError = "Login Error"
    }


    // Validation
    const val Required = "Required field"
    const val OldNewPasswordSame = "Old and new password should not be same"
    const val PasswordMinLength = "Passwords must be 6 characters long"
    const val MatchPassword = "Passwords must be matching"
    const val ValidEmail = "Must use a valid email"
    const val BlankField = "Must not be left blank"

    const val Back = "Back"
    const val ConfirmPassword = "Confirm Password"
    const val Email = "Email"
    const val FirstName = "First Name"
    const val LastName = "Last Name"
    const val Login = "Login"
    const val Next = "Next"
    const val Ok = "Ok"


}