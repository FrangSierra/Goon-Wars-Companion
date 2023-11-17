package com.durdinstudios.goonwarscollector.domain.persistence

import com.durdinstudios.goonwarscollector.domain.persistence.SharedPreferencesHelper.Companion.CURRENT_MIGRATION_VERSION
import com.minikorp.grove.Grove

/**
 * Shared preferences implementation for [AppPersistence].
 */
class SharedPreferencePersistence(private val sharedPreferencesHelper: SharedPreferencesHelper) : AppPersistence {
    private val migrationStrategy: MigrationStrategy = SharedPreferencesMigrationStrategy(sharedPreferencesHelper)
   // private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun <T> getElement(key: SettingKey, defaultValue: T?): Element? {
        val serializedElement = sharedPreferencesHelper.readString(key.generateKey())
        return serializedElement?.let { Setting(key, it) }
    }

    override fun <T> insert(key: SettingKey, value: T) {
        sharedPreferencesHelper.writeString(key.generateKey(), value.toString())
    }

    override fun clear(key: SettingKey) {
        sharedPreferencesHelper.clearValue(key.generateKey())
    }

    override fun clearAll() {
        sharedPreferencesHelper.clear()
    }

    override fun migrateSettingsIfNecessary(): Boolean {
        val oldVersion = sharedPreferencesHelper.getVersion()
        try {
            if (migrationStrategy.canMigrate(oldVersion, CURRENT_MIGRATION_VERSION)) {
                var current = 0
                while (current < CURRENT_MIGRATION_VERSION) {
                    Grove.d { "Migrating from $current to ${(current + 1)}" }
                    migrationStrategy.migrate(oldVersion, CURRENT_MIGRATION_VERSION, current, current + 1)
                    current++
                }
                sharedPreferencesHelper.persistVersion()
                //TODO Analytics
                return true
            }
            return false
        } catch (e: Throwable) {
            sharedPreferencesHelper.clear()
            sharedPreferencesHelper.persistVersion()
          //  crashlytics.recordException(e)
            //TODO Analytics
            return false
        }
    }
}