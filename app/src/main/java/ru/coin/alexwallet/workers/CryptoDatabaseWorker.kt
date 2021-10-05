package ru.coin.alexwallet.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.coin.alexwallet.storage.AppDatabase
import ru.coin.alexwallet.storage.CryptoCurrency
import ru.coin.alexwallet.storage.Users
import java.util.*

const val CRYPTO_NAMES = "ru.coin.alexwallet.workers.CRYPTO_NAMES"
const val TEST_USER = "ru.coin.alexwallet.workers.TEST_USER"

class CryptoDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val namesArray = inputData.getStringArray(CRYPTO_NAMES)
            val user = Users(UUID.randomUUID().toString(),TEST_USER)
            AppDatabase.getInstance()?.userDao()?.insertUser(user)
            if (namesArray != null) {
                for (name in namesArray) {
                    val crypto = CryptoCurrency(name.lowercase())
                        AppDatabase.getInstance()?.cryptoCurrencyDao()?.insertCrypto(crypto)
                }
                Result.success()
            } else {
                Result.failure()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure()
        }
    }

}
