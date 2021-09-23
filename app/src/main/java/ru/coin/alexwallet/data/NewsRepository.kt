package ru.coin.alexwallet.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getSearchResultStream(): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 5, initialLoadSize = 10, maxSize = 20),
            pagingSourceFactory = { NewsPagingSource(newsService, "crypto") }
        ).flow
    }

}