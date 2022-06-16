package ru.slatinin.nytnews.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slatinin.nytnews.storage.AppDatabase
import ru.slatinin.nytnews.storage.CryptoCurrency
import ru.slatinin.nytnews.storage.Users
import ru.slatinin.nytnews.utils.IntUtil
import java.util.*

const val NYT_NAMES = "ru.slatinin.nytnews.workers.NYT_NAMES"
const val TEST_USER = "ru.slatinin.nytnews.workersTEST_USER"
const val UNIQUE_NYT_DATABASE_WORK_TAG =
    "ru.slatinin.nytnews.workers.UNIQUE_NYT_DATABASE_WORK_TAG"
const val NYT_DATABASE_WORK_RESULT = "ru.slatinin.nytnews.workers.NYT_DATABASE_WORK_RESULT"

class NYTDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val namesArray = inputData.getStringArray(NYT_NAMES)
            val user = Users(UUID.randomUUID().toString(), TEST_USER)
            AppDatabase.getInstance()?.userDao()?.insertUser(user)
            if (namesArray != null) {
                for (item in namesArray) {
                    val cryptoArray = item.split("\\|")
                    val name = cryptoArray[0].lowercase()
                    val remoteId = IntUtil.parseIntegerOrGetZero(cryptoArray[1])
                    val crypto = CryptoCurrency(
                        name = name.lowercase(),
                        remoteId = remoteId,
                        marketPriceInteger = 0L,
                        marketPriceDecimal = 0L,
                        walletValueInteger = 0L,
                        walletValueDecimal = 0L
                    )
                    AppDatabase.getInstance()?.cryptoCurrencyDao()?.insertCrypto(crypto)
                }
                val data: Data = workDataOf(NYT_DATABASE_WORK_RESULT to true)
                Result.success(data)
            } else {
                val data: Data = workDataOf(NYT_DATABASE_WORK_RESULT to false)
                Result.failure(data)
            }
        } catch (ex: Exception) {
            val data: Data = workDataOf(NYT_DATABASE_WORK_RESULT to false)
            ex.printStackTrace()
            Result.failure(data)
        }
    }

}
