package com.durdinstudios.goonwarscollector.utils

import kotlinx.coroutines.channels.Channel

/**
 * A semaphore for coroutines
 *
 * @param capacity one of [Channel] capacity values
 * @see [java.util.concurrent.Semaphore]
 */
class SuspendSemaphore(
    permits: Int = 0,
    capacity: Int = Channel.UNLIMITED
) {

    private val channel = Channel<Unit>(capacity)

    init {
        repeat(permits) {
            channel.trySend(Unit)
        }
    }

    /**
     * Acquires the semaphore.
     */
    suspend fun acquire() {
        channel.receive()
    }

    /**
     * Releases the semaphore.
     */
    fun release() {
        channel.trySend(Unit)
    }
}
