package ru.coin.alexwallet.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getSearchResultStream(): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(newsService, "crypto") }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}