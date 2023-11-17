package com.durdinstudios.goonwarscollector.domain.session

import com.durdinstudios.goonwarscollector.core.BootstrapAction
import com.durdinstudios.goonwarscollector.core.arch.BaseSaga
import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.createAppStateSelector
import com.minikorp.duo.ActionContext
import com.minikorp.duo.Reducer
import com.minikorp.duo.State
import com.minikorp.duo.TypedReducer
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.Serializable

val userLoggedSelector = createAppStateSelector(p1 = { it.session }, selector = { it.userLogged })

data class UserSessionStatus(
    val userId: String? = null,
    val isLogged: Boolean = false,
    val isVerified: Boolean = false
) : Serializable

@State
data class SessionState(
    val userLogged: Resource<UserSessionStatus> = Resource.empty(),
    val recoverPasswordTask: Resource<String> = Resource.empty()
) : Serializable

@TypedReducer
class SessionSaga(di: DI) : BaseSaga<SessionState>(di) {
    private val controller: SessionController by instance()

    @TypedReducer.Fun
    suspend fun bootstrap(ctx: ActionContext<SessionState>, action: BootstrapAction) {

    }

    @TypedReducer.Fun
    suspend fun handleRecoverPassword(
        ctx: ActionContext<SessionState>,
        action: RecoverPasswordAction.Request
    ) {
        ctx.reduce { it.copy(recoverPasswordTask = Resource.loading()) }
        //controller.sendPasswordRecoveryEmail(action.email)
        //    .onSuccess { ctx.dispatch(RecoverPasswordAction.Response(Resource.success(action.email))) }
        //    .onFailure { ctx.dispatch(RecoverPasswordAction.Response(Resource.failure(it))) }
    }

    @TypedReducer.Fun
    suspend fun logout(
        ctx: ActionContext<SessionState>,
        action: LogoutAction
    ) {
        //controller.signOut()
        ctx.reduce { SessionState() }
    }

    @TypedReducer.Root
    override suspend fun reduce(ctx: ActionContext<SessionState>) {
        reduceTyped(ctx)
    }

}

@TypedReducer
class SessionReducer : Reducer<SessionState> {

    @TypedReducer.Fun
    fun handleRecoverPasswordResponse(
        state: SessionState,
        action: RecoverPasswordAction.Response
    ): SessionState {
        return state.copy(recoverPasswordTask = action.emailSent)
    }

    @TypedReducer.Root
    override suspend fun reduce(ctx: ActionContext<SessionState>) {
        ctx.mutableState = reduceTyped(ctx) ?: ctx.state
    }
}