package com.durdinstudios.goonwarscollector.domain.persistence

import android.content.Context
import android.util.Size
import com.durdinstudios.goonwarscollector.BuildConfig
import com.minikorp.grove.Grove
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Actual implementation of [AppPersistence] by [SharedPreferencesHelper] using android [SharedPreferences].
 */
class PersistenceController(context: Context, val moshi: Moshi) :
    AppPersistence by SharedPreferencePersistence(SharedPreferencesHelper(context)) {

    val adapterList: MutableMap<SettingKey, JsonAdapter<*>> = mutableMapOf()

    init {
        if (BuildConfig.DEBUG) {
          //  clearAll()
        }
    }

    inline fun <reified T> getValue(key: SettingKey, defaultValue: T): T {
        try {
            val element = getElement(key, defaultValue) ?: return defaultValue
            return element.toCustomType<T>()
        } catch (e: Exception) {
            Grove.e(e) { "Setting removed was : ${key.name} with tags ${key.tags.toList()}" }
            clearValue(key)
            return defaultValue
        }
    }

    inline fun <reified T> getListOfValues(key: SettingKey, defaultValue: List<T>): List<T> {
        try {
            val element = getElement(key, defaultValue) ?: return defaultValue
            return element.toCustomTypeList<T>()
        } catch (e: Exception) {
            Grove.e(e) { "Setting removed was : ${key.name} with tags ${key.tags.toList()}" }
            clearValue(key)
            return defaultValue
        }
    }

    inline fun <reified T> getNullableValue(key: SettingKey, defaultValue: T?): T? {
        val element = getElement(key, defaultValue) ?: return defaultValue
        return element.toCustomType<T>()
    }

    @Suppress("UndocumentedPublicFunction")
    fun setValue(key: SettingKey, value: Any) {
        insert(key, value)
    }

    @Suppress("UndocumentedPublicFunction")
    inline fun <reified T> setSerializedValue(key: SettingKey, value: T) {
        val adapter: JsonAdapter<T> = adapterList.getOrPut(key) { moshi.adapter<T>(T::class.java) } as JsonAdapter<T>
        val json = adapter.toJson(value)
        insert(key, json)
    }

    @Suppress("UndocumentedPublicFunction")
    inline fun <reified T> getSerializedValue(key: SettingKey, defaultValue: T?): T? {
        val adapter: JsonAdapter<T> = adapterList.getOrPut(key) { moshi.adapter<T>(T::class.java) } as JsonAdapter<T>
        val element = getElement(key, adapter.toJsonValue(defaultValue)) ?: return defaultValue
        return adapter.fromJson(element.value)
    }

    @Suppress("UndocumentedPublicFunction")
    inline fun <reified T> setSerializedValueList(key: SettingKey, value: List<T>) {
        val listMyData: Type = Types.newParameterizedType(MutableList::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> =
            adapterList.getOrPut(key) { moshi.adapter<List<T>>(listMyData) } as JsonAdapter<List<T>>
        val json = adapter.toJson(value)
        insert(key, json)
    }

    @Suppress("UndocumentedPublicFunction")
    inline fun <reified T> getSerializedValueList(key: SettingKey, defaultValue: List<T>?): List<T> {
        val listMyData: Type = Types.newParameterizedType(MutableList::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> =
            adapterList.getOrPut(key) { moshi.adapter<List<T>>(listMyData) } as JsonAdapter<List<T>>
        val element = getElement(key, adapter.toJsonValue(defaultValue)) ?: return defaultValue ?: emptyList()
        return adapter.fromJson(element.value) ?: emptyList()
    }

    @Suppress("UndocumentedPublicFunction")
    fun clearValue(key: SettingKey) {
        clear(key)
    }

    /**
     * Casts the actual [Element] to a custom type used by the app.
     */
    @Suppress("IMPLICIT_CAST_TO_ANY")
    inline fun <reified T> Element.toCustomType(): T {
        val mappedValue = when (T::class) {
            String::class -> value as T
            Boolean::class -> value.toBoolean() as T
            Int::class -> value.toInt() as T
            Float::class -> value.toFloat() as T
            Long::class -> value.toLong() as T
            Size::class -> {
                val string = this.value.split("x")
                string.let { Size(it[0].toInt(), it[1].toInt()) }
            }
            else -> throw ClassCastException("Setting cannot be cast to ${T::class.simpleName}")
        }
        return mappedValue as T
    }

    /**
     * Casts the actual [Element] to a custom [List] type used by the app.
     */
    @Suppress("IMPLICIT_CAST_TO_ANY")
    inline fun <reified T> Element.toCustomTypeList(): List<T> {
        val list = value.removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
        if (list.isEmpty()) return emptyList()
        return when (T::class) {
            String::class -> list.map { it as T }
            Boolean::class -> list.map { it.toBoolean() as T }
            Int::class -> list.map { it.toInt() as T }
            Float::class -> list.map { it.toFloat() as T }
            Long::class -> list.map { it.toLong() as T }
            else -> throw ClassCastException("Setting cannot be cast to ${T::class.simpleName}")
        }
    }
}