package com.durdinstudios.goonwarscollector.domain.persistence

import android.content.Context
import android.content.SharedPreferences

/**
 * Helper class to work with [SharedPreferences].
 */
class SharedPreferencesHelper(context: Context) {

    companion object {
        const val TERMS_PREFERENCE_FILE = "terms_settings"
        const val PREFERENCE_FILE = "gob_companion_settings"
        const val CURRENT_MIGRATION_VERSION = 1
        private const val VERSION_KEY = "version"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
    private val termSharedPreferences: SharedPreferences = context.getSharedPreferences(TERMS_PREFERENCE_FILE, Context.MODE_PRIVATE)

    @Suppress("UndocumentedPublicFunction")
    fun writeString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    @Suppress("UndocumentedPublicFunction")
    fun readString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    @Suppress("UndocumentedPublicFunction")
    fun getVersion(): Int {
        return sharedPreferences.getInt(VERSION_KEY, 0)
    }

    @Suppress("UndocumentedPublicFunction")
    fun persistVersion() {
        return sharedPreferences.edit().putInt(VERSION_KEY, CURRENT_MIGRATION_VERSION).apply()
    }

    @Suppress("UndocumentedPublicFunction")
    fun clear() {
       sharedPreferences.edit().clear().apply()
    }


    @Suppress("UndocumentedPublicFunction")
    fun clearValue(key: String) {
        return sharedPreferences.edit().remove(key).apply()
    }
}