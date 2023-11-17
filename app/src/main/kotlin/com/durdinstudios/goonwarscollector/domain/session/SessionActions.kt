package com.durdinstudios.goonwarscollector.domain.session

import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.RootLogAction
import com.minikorp.duo.Action
import com.minikorp.duo.TypedAction

//TODO This code is here in case we wanna implement auth on the future to keep data across devices

interface SignInAction {
    @TypedAction
    data class Request(val email: String, val password: String) : Action, RootLogAction

    @TypedAction
    data class Response(val emailSent: Resource<String>) : Action
}

interface LoginAction {
    @TypedAction
    data class Request(val email: String, val password: String) : Action, RootLogAction

    @TypedAction
    data class Response(val emailSent: Resource<String>) : Action
}


interface RecoverPasswordAction {
    @TypedAction
    data class Request(val email: String) : Action, RootLogAction

    @TypedAction
    data class Response(val emailSent: Resource<String>) : Action
}

@TypedAction
object LogoutAction : Action