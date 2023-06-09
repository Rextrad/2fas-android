package com.twofasapp.services.workmanager

import android.content.Context
import androidx.work.*
import com.twofasapp.backup.domain.SyncBackupWorkDispatcher
import com.twofasapp.backup.domain.SyncBackupTrigger
import timber.log.Timber

class SyncBackupWorkDispatcherImpl(
    private val context: Context,
    private val remoteBackupStatusPreference: com.twofasapp.prefs.usecase.RemoteBackupStatusPreference,
) : SyncBackupWorkDispatcher {

    override fun dispatch(trigger: SyncBackupTrigger, password: String?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncBackupWork>()
            .setInputData(Data.Builder().apply {
                putString(SyncBackupWork.ARG_TRIGGER, trigger.name)
                password?.let { putString(SyncBackupWork.ARG_PASSWORD, it) }
            }.build())
            .setConstraints(constraints)
            .build()

        if (remoteBackupStatusPreference.get().state != com.twofasapp.prefs.model.RemoteBackupStatus.State.ACTIVE) {
            return
        }

        val enqueuedWork = findEnqueuedWork()

        // No jobs in queue -> append new one
        if (enqueuedWork == null) {
            Timber.d("Append new SyncBackupWork")
            WorkManager.getInstance(context).enqueueUniqueWork("SyncBackupWork", ExistingWorkPolicy.APPEND_OR_REPLACE, request)
        }

        // There is a job in queue with retry policy -> replace with new one
        if ((enqueuedWork != null && enqueuedWork.runAttemptCount > 0) || enqueuedWork?.state == WorkInfo.State.BLOCKED) {
            Timber.d("Append new SyncBackupWork by replacing retry work")
            WorkManager.getInstance(context).enqueueUniqueWork("SyncBackupWork", ExistingWorkPolicy.REPLACE, request)
        }
    }

    private fun findEnqueuedWork() = WorkManager.getInstance(context).getWorkInfosForUniqueWork("SyncBackupWork").get()
        .find { it.state == WorkInfo.State.BLOCKED || it.state == WorkInfo.State.ENQUEUED }?.let {
            Timber.d("There is a work in queue: id=${it.id} runAttemptCount=${it.runAttemptCount}")
            it
        }
}