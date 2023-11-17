package com.durdinstudios.goonwarscollector.domain.analytics

import android.os.Bundle
import com.durdinstudios.goonwarscollector.core.appContext

interface AnalyticsController {
    fun logAnalyticsEvent(name: String, info: Bundle)
    fun setUserId(userId: String)
    fun setUserProperty(propertyKey: String, value: String?)
    fun setCurrentScreen(screenName: String)
}

class AnalyticsControllerImpl : AnalyticsController {
    //private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(appContext) }

    override fun logAnalyticsEvent(name: String, info: Bundle) {
    //    firebaseAnalytics.logEvent(name, info)
    }

    override fun setUserId(userId: String) {
      //  firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(propertyKey: String, value: String?) {
      //  firebaseAnalytics.setUserProperty(propertyKey, value)
    }

    override fun setCurrentScreen(screenName: String) {
      //  val bundle = Bundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName) }
      //  firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}