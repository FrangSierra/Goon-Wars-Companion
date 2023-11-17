package com.durdinstudios.goonwarscollector.domain.persistence

import android.content.SharedPreferences

/**
 * Represents a cache/disk migration strategy based on the previous version of the persisted data.
 */
interface MigrationStrategy {
    @Suppress("UndocumentedPublicFunction")
    fun migrate(start: Int, end: Int, from: Int, to: Int)
    /**
     * Returns true if the actual strategy needs to migrate something based on the given tags.
     */
    fun canMigrate(start: Int, end: Int): Boolean
}

/**
 * Default implementation for [SharedPreferences] migration strategy.
 */
class SharedPreferencesMigrationStrategy(private val sharedPreferencesHelper: SharedPreferencesHelper) :
    MigrationStrategy {

    @Suppress("UndocumentedPublicFunction")
    override fun migrate(start: Int, end: Int, from: Int, to: Int) {
        if (from < 1 && to >= 1) {

        }

        if (from < 2 && to >= 2) {

        }
    }

    @Suppress("UndocumentedPublicFunction")
    override fun canMigrate(start: Int, end: Int): Boolean {
        // Add the cases for which migration is required.
        return start < 1 && end == 1
    }
}