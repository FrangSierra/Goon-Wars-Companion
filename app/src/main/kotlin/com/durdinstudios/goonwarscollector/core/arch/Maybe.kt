@file:Suppress("UNCHECKED_CAST", "RedundantVisibilityModifier")
package com.durdinstudios.goonwarscollector.core.arch

import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [T]
 * or a failure with an arbitrary [Throwable] exception.
 */
@JvmInline
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
@SinceKotlin("1.3")
public value class Maybe<out T> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any?
) {
    // discovery

    /**
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isFailure] returns `false`.
     */
    public val isSuccess: Boolean get() = value !is Failure

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    public val isFailure: Boolean get() = value is Failure

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][Maybe.isSuccess] or `null`
     * if it is [failure][Maybe.isFailure].
     *
     * This function is a shorthand for `getOrElse { null }` (see [getOrElse]) or
     * `fold(onSuccess = { it }, onFailure = { null })` (see [fold]).
     */
    public fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    /**
     * Returns the encapsulated [Throwable] exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     */
    public fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    /**
     * Returns a string `Success(v)` if this instance represents [success][Maybe.isSuccess]
     * where `v` is a string representation of the value or a string `Failure(x)` if
     * it is [failure][isFailure] where `x` is a string representation of the exception.
     */
    public override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    // companion with constructors

    /**
     * Companion object for [Maybe] class that contains its constructor functions
     * [success] and [failure].
     */
    public companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")
        @JvmName("success")
        public fun <T> success(value: T): Maybe<T> =
            Maybe(value)

        /**
         * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
         */
        @Suppress("INAPPLICABLE_JVM_NAME")
        @JvmName("failure")
        public fun <T> failure(exception: Throwable): Maybe<T> =
            Maybe(createFailure(exception))
    }

    internal class Failure(
        @JvmField
        val exception: Throwable
    ) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

/**
 * Creates an instance of internal marker [Maybe.Failure] class to
 * make sure that this class is not exposed in ABI.
 */
@PublishedApi
@SinceKotlin("1.3")
internal fun createFailure(exception: Throwable): Any =
    Maybe.Failure(exception)

/**
 * Throws exception if the result is failure. This internal function minimizes
 * inlined bytecode for [getOrThrow] and makes sure that in the future we can
 * add some exception-augmenting logic here (if needed).
 */
@PublishedApi
@SinceKotlin("1.3")
internal fun Maybe<*>.throwOnFailure() {
    if (value is Maybe.Failure) throw value.exception
}

/**
 * Calls the specified function [block] and returns its encapsulated result if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
@SinceKotlin("1.3")
public inline fun <R> maybeCatching(block: () -> R): Maybe<R> {
    return try {
        Maybe.success(block())
    } catch (e: Throwable) {
        Maybe.failure(e)
    }
}

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its encapsulated result if invocation was successful,
 * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
 */
@SinceKotlin("1.3")
public inline fun <T, R> T.maybeCatching(block: T.() -> R): Maybe<R> {
    return try {
        Maybe.success(block())
    } catch (e: Throwable) {
        Maybe.failure(e)
    }
}

// -- extensions ---

/**
 * Returns the encapsulated value if this instance represents [success][Maybe.isSuccess] or throws the encapsulated [Throwable] exception
 * if it is [failure][Maybe.isFailure].
 *
 * This function is a shorthand for `getOrElse { throw it }` (see [getOrElse]).
 */
@SinceKotlin("1.3")
public fun <T> Maybe<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
}

/**
 * Returns the encapsulated value if this instance represents [success][Maybe.isSuccess] or the
 * result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][Maybe.isFailure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [onFailure] function.
 *
 * This function is a shorthand for `fold(onSuccess = { it }, onFailure = onFailure)` (see [fold]).
 */
public inline fun <R, T : R> Maybe<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    return when (val exception = exceptionOrNull()) {
        null -> value as T
        else -> onFailure(exception)
    }
}

/**
 * Returns the encapsulated value if this instance represents [success][Maybe.isSuccess] or the
 * [defaultValue] if it is [failure][Maybe.isFailure].
 *
 * This function is a shorthand for `getOrElse { defaultValue }` (see [getOrElse]).
 */
@SinceKotlin("1.3")
public fun <R, T : R> Maybe<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return value as T
}

/**
 * Returns the result of [onSuccess] for the encapsulated value if this instance represents [success][Maybe.isSuccess]
 * or the result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][Maybe.isFailure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [onSuccess] or by [onFailure] function.
 */
@SinceKotlin("1.3")
public inline fun <R, T> Maybe<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(exception)
    }
}

// transformation

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Maybe.isSuccess] or the
 * original encapsulated [Throwable] exception if it is [failure][Maybe.isFailure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
 * See [mapCatching] for an alternative that encapsulates exceptions.
 */
@SinceKotlin("1.3")
public inline fun <R, T> Maybe<T>.map(transform: (value: T) -> R): Maybe<R> {
    return when {
        isSuccess -> Maybe.success(transform(value as T))
        else -> Maybe(value)
    }
}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Maybe.isSuccess] or the
 * original encapsulated [Throwable] exception if it is [failure][Maybe.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
 * See [map] for an alternative that rethrows exceptions from `transform` function.
 */
@SinceKotlin("1.3")
public inline fun <R, T> Maybe<T>.mapCatching(transform: (value: T) -> R): Maybe<R> {
    return when {
        isSuccess -> maybeCatching { transform(value as T) }
        else -> Maybe(value)
    }
}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
 * if this instance represents [failure][Maybe.isFailure] or the
 * original encapsulated value if it is [success][Maybe.isSuccess].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
 * See [recoverCatching] for an alternative that encapsulates exceptions.
 */
@SinceKotlin("1.3")
public inline fun <R, T : R> Maybe<T>.recover(transform: (exception: Throwable) -> R): Maybe<R> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> Maybe.success(transform(exception))
    }
}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
 * if this instance represents [failure][Maybe.isFailure] or the
 * original encapsulated value if it is [success][Maybe.isSuccess].
 *
 * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
 * See [recover] for an alternative that rethrows exceptions.
 */
@SinceKotlin("1.3")
public inline fun <R, T : R> Maybe<T>.recoverCatching(transform: (exception: Throwable) -> R): Maybe<R> {
    val value = value // workaround for inline classes BE bug
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> maybeCatching { transform(exception) }
    }
}

// "peek" onto value/exception and pipe

/**
 * Performs the given [action] on the encapsulated [Throwable] exception if this instance represents [failure][Maybe.isFailure].
 * Returns the original `Result` unchanged.
 */
@SinceKotlin("1.3")
public inline fun <T> Maybe<T>.onFailure(action: (exception: Throwable) -> Unit): Maybe<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [success][Maybe.isSuccess].
 * Returns the original `Result` unchanged.
 */
@SinceKotlin("1.3")
public inline fun <T> Maybe<T>.onSuccess(action: (value: T) -> Unit): Maybe<T> {
    if (isSuccess) action(value as T)
    return this
}

// -------------------
