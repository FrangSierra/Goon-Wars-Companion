package com.durdinstudios.goonwarscollector.domain.session

import com.durdinstudios.goonwarscollector.core.arch.Maybe
import com.durdinstudios.goonwarscollector.core.arch.maybeCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SessionController {
    suspend fun sendPasswordRecoveryEmail(email: String): Maybe<Void>
    fun signOut()
}

class SessionControllerImpl() : SessionController {

    override suspend fun sendPasswordRecoveryEmail(email: String): Maybe<Void> =
        withContext(Dispatchers.IO) {
            maybeCatching {
                TODO()
            }
        }

    override fun signOut() {
        // signOut()
    }
}