package com.durdinstudios.goonwarscollector.domain.analytics

import android.os.Bundle
import android.util.Size
import com.durdinstudios.goonwarscollector.BuildConfig
import com.minikorp.duo.*
import com.minikorp.grove.Grove

class AnalyticsEventAction(event: AnalyticsEvent) : AnalyticsAction {
    override val analyticsEvent: AnalyticsEvent = event
}

/**
 * Base implementation for an analytics event.
 */
interface AnalyticsEvent {
    val eventName: String
    val analyticsMap: Map<String, Any?>
}

interface SetUserIdAction : AnalyticsAction {
    val userId: String
}

@TypedAction
interface AnalyticsAction : CustomLogAction {
    val logAction get() = false
    override val logConfig: ActionLogConfig
        get() = ActionLogConfig(silent = true, logAction = logAction)
    val analyticsEvent: AnalyticsEvent?
    fun shouldSendAnalyticsEvent(): Boolean = true
}

@TypedAction
interface AnalyticsChangeScreenAction : AnalyticsAction {
    val screenName: String?
}

@TypedAction
class ChangeScreenAction(screen: String) : AnalyticsChangeScreenAction {
    override val analyticsEvent: AnalyticsEvent? = null
    override val screenName = screen
}

/**
 * Middleware in charge of tracking the analytics action from the app and upload them to the actual analytics provider.
 */
class AnalyticsMiddleware<S : Any> : Middleware<S> {
    private val analyticsController = AnalyticsControllerImpl()

    override suspend fun intercept(ctx: ActionContext<S>, chain: Chain<S>) {
        val action = ctx.action
        if (action !is AnalyticsAction) return chain.proceed(ctx)
        if (!action.shouldSendAnalyticsEvent()) return chain.proceed(ctx)

        action.analyticsEvent?.let { event ->
            if (BuildConfig.DEBUG) {
                Grove.d { "New Analytics event : ${event.eventName} with elements ${event.analyticsMap}" }
            } else {
                analyticsController.logAnalyticsEvent(event.eventName, event.analyticsMap.toAnalyticsBundle())
            }
        }

        if (action is ChangeScreenAction) {
            if (BuildConfig.DEBUG) {
                Grove.d { "New Analytics screen change to: ${action.screenName}" }
            } else {
                analyticsController.setCurrentScreen(action.screenName)
            }
        }

        if (action is SetUserIdAction) {
            if (BuildConfig.DEBUG) {
                Grove.d { "New Analytics user ID: ${action.userId}" }
            } else {
                analyticsController.setUserId(action.userId)
            }
        }

        return chain.proceed(ctx)
    }
}

/**
 * Cast the given map of values and keys to a bundle friendly for analytics libraries.
 */
@SuppressWarnings("SpreadOperator")
private fun Map<String, *>.toAnalyticsBundle(outBundle: Bundle = Bundle()): Bundle {
    val values = this.entries.map { it.key to it.value }
    return analyticsBundleOf(pairs = *values.toTypedArray(), outBundle = outBundle)
}

/**
 * Converts a list of pairs(key-value) in an Android Bundle.
 */
fun analyticsBundleOf(vararg pairs: Pair<String, *>, outBundle: Bundle = Bundle()): Bundle {
    outBundle.apply {
        pairs.filter { it.second != null }.forEach {
            val (k, v) = it

            when (v) {
                is String -> putString(k, v)
                is Char -> putChar(k, v)
                is CharSequence -> putCharSequence(k, v)
                is Float -> putFloat(k, v)
                is Int -> putInt(k, v)
                is Long -> putLong(k, v)
                is Double -> putDouble(k, v)
                is Enum<*> -> putString(k, v.name.lowercase())
                is Boolean -> putBoolean(k, v)
                is Size -> putString(k, "${v.width}x${v.height}")
                else -> throw IllegalArgumentException("$v is of a type that is not currently supported for analytics")
            }
        }
    }
    return outBundle
}

@Suppress("UndocumentedPublicClass")
class NullAnalyticsValueException(key: String) : NullPointerException("$key is null on the analytics bundle")