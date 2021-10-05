package ru.coin.alexwallet.storage.converters

import ru.coin.alexwallet.data.NewsItem
import ru.coin.alexwallet.data.NewsMultimedia
import ru.coin.alexwallet.storage.AppDatabase
import ru.coin.alexwallet.storage.News
import ru.coin.alexwallet.workers.TEST_USER
import java.util.*
import kotlin.collections.ArrayList

class NewsItemsToNewsConverter {
    companion object {
        suspend fun getNewsEntity(items: List<NewsItem>): ArrayList<News> {
            var userId = AppDatabase.getInstance()?.userDao()?.getUserByName(TEST_USER)?.userId
            if (userId == null) {
                userId = ""
            }
            val newsList = ArrayList<News>(items.size)
            for (i in items.indices) {
                val imageUrl: String =
                    if (items[i].multimedia.isNotEmpty()) items[i].multimedia[0].imageUrl else ""
                val newsImageSubtype: String =
                    if (items[i].multimedia.isNotEmpty()) items[i].multimedia[0].newsImageSubtype else ""
                val news = News(
                    UUID.randomUUID().toString(),
                    userId,
                    leadParagraph = items[i].leadParagraph,
                    topic = items[i].topic,
                    newsUrl = items[i].newsUrl,
                    imageUrl = imageUrl,
                    newsImageSubType = newsImageSubtype,
                    pubDate = items[i].pubDate,
                    dateInMillis = Calendar.getInstance().timeInMillis
                )
                newsList.add(news)
            }
            return newsList
        }

        fun getNewsItems(newsList: List<News>): List<NewsItem> {
            val newsItems = ArrayList<NewsItem>()
            for (i in newsList.indices) {
                val multimedia = ArrayList<NewsMultimedia>()
                if (newsList[i].imageUrl.isNotEmpty()) {
                    multimedia.add(
                        NewsMultimedia(
                            imageUrl = newsList[i].imageUrl,
                            newsImageSubtype = newsList[i].newsImageSubType
                        )
                    )
                }
                val newsItem = NewsItem(
                    leadParagraph = newsList[i].leadParagraph,
                    topic = newsList[i].topic,
                    newsUrl = newsList[i].newsUrl,
                    multimedia = multimedia,
                    pubDate = newsList[i].pubDate
                )
                newsItems.add(newsItem)
            }
            return newsItems
        }
    }
}