package com.durdinstudios.goonwarscollector.core.deeplinks

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.durdinstudios.goonwarscollector.utils.SuspendSemaphore
import com.minikorp.grove.Grove
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import kotlin.system.measureTimeMillis

/**
 * Hub for application deep links. It process each [Intent] received on the app.
 * Those can be intercepted by any of the attached [Interceptor] and avoid to be emitted by the dispatcher [viewLiveData].
 */
@SuppressWarnings("UndocumentedPublicFunction", "UndocumentedPublicClass", "UnnecessaryAbstractClass")
abstract class DeepLinkDispatcher {
    protected open val viewLiveData: MutableLiveData<Uri> = MutableLiveData(null)
    protected val interceptors: MutableList<Interceptor> = mutableListOf()
    private val uriTransformers: MutableList<UriTransformer> = mutableListOf()
    protected val scope = CoroutineScope(Job())
    private val pipeline: Deque<Uri> = LinkedBlockingDeque()
    private val actorSemaphore = SuspendSemaphore()

    val pendingDeepLink: Uri? get() = pipeline.peek()

    protected open fun handleDeeplinkPipeline() {
        scope.launch {
            while (isActive) {
                actorSemaphore.acquire()
                pipeline.safePop()?.let { uri ->
                    measureTimeMillis {
                        Grove.d { "[DeepLinkManagement] processing uri $uri" }
                        for (interceptor in interceptors) {
                            if (interceptor.intercept(uri)) {
                                return@launch
                            }
                        }

                        withContext(Dispatchers.Main) {
                            viewLiveData.value = uri
                        }
                    }
                }
            }
        }
    }

    /**
     * Clear the live data value, avoiding the previous value to be emitted again.
     */
    fun clearDeeplinkLiveData() = scope.launch {
        withContext(Dispatchers.Main) {
            viewLiveData.value = Uri.EMPTY
        }
    }

    open suspend fun processUri(uri: Uri?) {
        scope.launch {
            uri?.let {
                val processedUri = uriTransformers.fold(it) { uri, transformer ->
                    transformer.transform(uri)
                }

                pipeline.add(processedUri)
                actorSemaphore.release()
            }
        }
    }

    fun getLiveData(): LiveData<Uri> = viewLiveData

    fun clear() {
        scope.launch {
            if (pipeline.isNotEmpty()) {
                Grove.d { "[DeepLinkManagement] Cleaning pipeline" }
                pipeline.clear()
            }
        }
    }

    fun initialize() {
        Grove.d { "[DeepLinkManagement] Initializing" }
        handleDeeplinkPipeline()
    }

    fun addInterceptor(interceptor: Interceptor) {
        synchronized(this) {
            interceptors += interceptor
        }
    }

    fun removeInterceptor(interceptor: Interceptor) {
        synchronized(this) {
            interceptors -= interceptor
        }
    }

    fun addUriTransformer(transformer: UriTransformer) {
        synchronized(this) {
            uriTransformers += transformer
        }
    }

    fun removeUriTransformer(transformer: UriTransformer) {
        synchronized(this) {
            uriTransformers -= transformer
        }
    }

    /**
     * Interceptor that will be called for every [Intent] to check if the given [Uri] needs to be handled on an special way.
     */
    interface Interceptor {
        /**
         * @return true if the [Interceptor] has handled the [Uri].
         */
        suspend fun intercept(uri: Uri): Boolean
    }

    /**
     * Function that will be called for every [Uri] in case they need further transformations to meet Compose Navigation standards.
     */
    interface UriTransformer {
        /**
         * @return the processed [Uri].
         */
        suspend fun transform(uri: Uri): Uri
    }

    private fun <T> Deque<T>.safePop(): T? {
        return try {
            this.pop()
        } catch (e: Exception) {
            Grove.e(e)
            null
        }
    }
}

@SuppressWarnings("UndocumentedPublicClass")
class DeepLinkDispatcherImpl : DeepLinkDispatcher()
