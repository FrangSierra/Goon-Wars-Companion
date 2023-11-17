package com.durdinstudios.goonwarscollector.core.arch

import com.durdinstudios.goonwarscollector.core.AppState
import com.minikorp.duo.*
import org.kodein.di.DI
import org.kodein.di.DIAware

typealias AppStore = Store<AppState>

/**
 * Base [Saga] implementation which implements [DIAware] for all the dependency injection.
 */
abstract class BaseSaga<T : Any>(override val di: DI) : Reducer<T>, DIAware {
    fun ActionContext<T>.reduce(fn: (T) -> T) {
        mutableState = fn(state)
    }
}

interface RootLogAction : CustomLogAction {
    override val logConfig: ActionLogConfig
        get() = ActionLogConfig(effectAction = true)
}