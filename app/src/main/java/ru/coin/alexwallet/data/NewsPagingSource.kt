package ru.coin.alexwallet.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.coin.alexwallet.storage.AppDatabase
import ru.coin.alexwallet.storage.converters.NewsItemsToNewsConverter
import java.util.*

private const val STARTING_PAGE_INDEX = 1
private const val THREE_HOURS = 1000 * 60 * 60 * 3

class NewsPagingSource(
    private val service: NewsService,
    private val query: String
) : PagingSource<Int, NewsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {

        if (AppDatabase.getInstance()?.newsDao()?.hasAnyRecord() == 1) {
            val count = AppDatabase.getInstance()
                ?.newsDao()?.isNotTooOld(Calendar.getInstance().timeInMillis - THREE_HOURS)
            if (count != null && count > 19) {
                val newsItems = AppDatabase.getInstance()?.newsDao()?.getLastTwenty()?.let {
                    NewsItemsToNewsConverter.getNewsItems(
                        it
                    )
                }
                newsItems?.let {
                    return LoadResult.Page(
                        data = it,
                        nextKey = null,
                        prevKey = null
                    )
                }
            }
        }

        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.searchPhotos(query, page - 1)
            val newsItems = response.results.docs
            AppDatabase.getInstance()?.newsDao()
                ?.insertNews(NewsItemsToNewsConverter.getNewsEntity(newsItems))
            LoadResult.Page(
                data = newsItems,
                nextKey = if (page > STARTING_PAGE_INDEX) null else page + 1,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
