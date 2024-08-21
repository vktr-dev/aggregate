package dev.jokku.newsdata.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.hilt.components.SingletonComponent
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

@dagger.hilt.EntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun hiltWorkerFactory(): HiltWorkerFactory
}

private const val WORKER_CLASS_NAME = "RouterWorkerDelegateClassName"

internal fun KClass<out CoroutineWorker>.delegatedData() =
    Data.Builder()
        .putString(WORKER_CLASS_NAME, qualifiedName)
        .build()

class DelegatingWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    // get worker name from key/value worker params input data
    private val workerClassName =
        workerParams.inputData.getString(WORKER_CLASS_NAME) ?: ""
    private val delegateWorker =
        // Call a Hilt WorkerFactory from SingletonComponent and ask it to create our worker
        dagger.hilt.android.EntryPointAccessors.fromApplication<HiltWorkerFactoryEntryPoint>(appContext)
            .hiltWorkerFactory()
            .createWorker(appContext, workerClassName, workerParams) as? CoroutineWorker
            ?: throw IllegalArgumentException("Unable to find appropriate worker")

    override suspend fun getForegroundInfo(): ForegroundInfo =
        delegateWorker.getForegroundInfo()

    override suspend fun doWork(): Result =
        delegateWorker.doWork()
}