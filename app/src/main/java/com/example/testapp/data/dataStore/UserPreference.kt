package com.example.testapp.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testapp.Model.common.User
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class UserPreference(context: Context) {

    private val dataStore = context.dataStore
    private val activeUserKey = stringPreferencesKey("active_user")
    private val _isUserFetched = MutableStateFlow(false)
    private val _isUserLoggedIn = MutableStateFlow(false)

    val isUserFetched get() = _isUserFetched
    val isUserLoggedIn get() = _isUserLoggedIn


    init {
        GlobalScope.launch {
            val user = getUser()
            _isUserLoggedIn.value = user != null
            delay(200)
            _isUserFetched.value = true
        }
    }

    suspend fun saveUser(user: User) {
        val userString = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[activeUserKey] = userString
        }
        _isUserLoggedIn.value = true
    }

    suspend fun getUser(): User? {
        dataStore.data.first()[activeUserKey]?.let { userData ->
            return Gson().fromJson(userData, User::class.java)
        }
        return null
    }

    suspend fun getUserToken(): String? {
        dataStore.data.first()[activeUserKey]?.let { userData ->
            val user = Gson().fromJson(userData, User::class.java)
            return user.accessToken
        }
        return null
    }

    suspend fun clearUser() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
        _isUserLoggedIn.value = false
    }


}