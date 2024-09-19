package dev.aggregate.sync

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aggregate.sync.initializers.SYNC_FAVORITE_TOP_HEADLINES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

/**
 * Reports on if synchronization is in progress
 */
interface SyncStatusMonitor {
    val isSyncing: Flow<Boolean>
}

/**
 * [SyncStatusMonitor] backed by [WorkInfo] from [WorkManager]
 */
class WorkManagerSyncStatusMonitor @Inject constructor(
    @ApplicationContext context: Context
) : SyncStatusMonitor {
    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(SYNC_FAVORITE_TOP_HEADLINES)
            .map { it.anyRunning }
            .asFlow()
            // let to get the latest emitted value dropping intermediary
            .conflate()
}

private val List<WorkInfo>.anyRunning get() = any { it.state == WorkInfo.State.RUNNING }
