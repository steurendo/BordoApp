package com.steurendo.bordo.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class AppPreference<T>(
    val key: Preferences.Key<T>,
    val defaultValue: T
)

object AppPreferencesKeys {
    val showOnboarding = AppPreference(
        key = booleanPreferencesKey("show_onboarding"),
        defaultValue = true
    )
}

object AppPreferences {
    private const val DATASTORE_NAME = "bordo_preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    internal suspend fun <T> setValue(value: T, preference: AppPreference<T>, context: Context) {
        context.dataStore.edit { it[preference.key] = value }
    }

    internal fun <T> getValueFlow(preference: AppPreference<T>, context: Context): Flow<T> =
        context.dataStore.data.catch { exception ->
            when (exception) {
                is IOException -> emit(emptyPreferences())
                else -> throw exception
            }
        }.map { it[preference.key] ?: preference.defaultValue }
}