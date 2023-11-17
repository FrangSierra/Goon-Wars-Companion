package com.durdinstudios.goonwarscollector.domain.persistence

/**
 * Represents a generic app persitance based on [SettingKey] to retrive [Element].
 */
interface AppPersistence {

    fun <T> getElement(
        key: SettingKey,
        defaultValue: T?
    ): Element?

    fun <T> insert(
        key: SettingKey,
        value: T
    )

    fun clear(key: SettingKey)

    fun clearAll()

    fun migrateSettingsIfNecessary(): Boolean
}

/**
 * Representation of a key/value element.
 */
interface Element {
    val key: Key
    val value: String

    /**
     * Custom key that identifies the [Element]. It uses [Tag] to allow multi values settings.
     */
    interface Key {
        operator fun invoke(vararg tagValues: SettingTag<*> = emptyArray()): SettingKey {
            return SettingKey(this::class.simpleName!!, tagValues.toList())
        }

        /**
         * Tag associated to a [SettingKey] that represents on of the possibly cases stored for the actual [Element].
         */
        interface Tag<E> {
            operator fun invoke(value: E): SettingTag<E> {
                return SettingTag(this::class.simpleName!!, value)
            }
        }
    }
}

/**
 * Key value setting element.
 */
class Setting(
    override val key: Element.Key,
    override val value: String
) : Element

/**
 * Key of an actual [Setting] with multiple tags associated to represent multi values.
 */
class SettingKey(
    val name: String,
    val tags: List<SettingTag<*>>
) : Element.Key

/**
 * Transforms on a string based item the actual key to be easier to store.
 */
fun SettingKey.generateKey(): String =
    name.let { keyName ->
        return if (tags.isEmpty()) keyName
        else {
            val tags = tags.joinToString { ".${it.tag}-${it.value}" }
            "$keyName$tags"
        }
    }

/**
 * [Element.Key.Tag] implementation.
 */
class SettingTag<T>(val tag: String, val value: T) : Element.Key.Tag<T>
