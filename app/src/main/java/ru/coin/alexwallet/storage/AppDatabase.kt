package ru.coin.alexwallet.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import ru.coin.alexwallet.DATABASE_NAME
import ru.coin.alexwallet.R
import ru.coin.alexwallet.workers.CRYPTO_NAMES
import ru.coin.alexwallet.workers.CryptoDatabaseWorker
import ru.coin.alexwallet.workers.UNIQUE_CRYPTO_DATABASE_WORK_TAG

@Database(
    entities = [Users::class, News::class, CryptoCurrency::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun createInstance(context: Context): AppDatabase {
            return instance ?: buildDatabase(context).also { instance = it }

        }

        fun getInstance(): AppDatabase? {
            return instance
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val cryptoNames = context.resources.getStringArray(R.array.crypto_names)
                        val request = OneTimeWorkRequestBuilder<CryptoDatabaseWorker>()
                            .setInputData(workDataOf(CRYPTO_NAMES to cryptoNames))
                            .build()
                        WorkManager.getInstance(context).enqueueUniqueWork(
                            UNIQUE_CRYPTO_DATABASE_WORK_TAG, ExistingWorkPolicy.REPLACE, request
                        )
                    }
                })
                .build()
            db.query("select 1", null)
            return db
        }
    }
}