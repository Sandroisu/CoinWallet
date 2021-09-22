package ru.coin.alexwallet.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getSearchResultStream(): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 10),
            pagingSourceFactory = { NewsPagingSource(newsService, "crypto") }
        ).flow
    }

}