package ru.coin.alexwallet.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import ru.coin.alexwallet.data.viewdata.NewsItem
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getSearchResultStream(query:String): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 5, initialLoadSize = 10, maxSize = 20),
            pagingSourceFactory = { NewsPagingSource(newsService, query = query) }
        ).flow
    }

}