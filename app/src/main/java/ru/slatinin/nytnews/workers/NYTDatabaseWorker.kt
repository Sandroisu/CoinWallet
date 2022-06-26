package ru.slatinin.nytnews.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slatinin.nytnews.storage.AppDatabase
import ru.slatinin.nytnews.storage.Users
import ru.slatinin.nytnews.utils.IntUtil
import java.util.*

const val NYT_NAMES = "ru.slatinin.nytnews.workers.NYT_NAMES"
const val UNIQUE_NYT_DATABASE_WORK_TAG =
    "ru.slatinin.nytnews.workers.UNIQUE_NYT_DATABASE_WORK_TAG"
const val NYT_DATABASE_WORK_RESULT = "ru.slatinin.nytnews.workers.NYT_DATABASE_WORK_RESULT"

class NYTDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = Result.success()

}
