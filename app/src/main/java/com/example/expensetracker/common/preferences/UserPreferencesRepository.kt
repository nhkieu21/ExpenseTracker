package com.example.expensetracker.common.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val IS_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        val USER_NAME = stringPreferencesKey("user_name")
        val MONTHLY_SALARY = doublePreferencesKey("monthly_salary")
    }

    val isOnboardingCompleted: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_ONBOARDING_COMPLETED] ?: false
        }

    val userName: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_NAME] ?: ""
        }

    val monthlySalary: Flow<Double> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.MONTHLY_SALARY] ?: 0.0
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun saveUserData(name: String, monthlySalary: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.MONTHLY_SALARY] = monthlySalary
        }
    }
}