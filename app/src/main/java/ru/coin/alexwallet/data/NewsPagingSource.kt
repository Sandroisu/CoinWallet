package ru.coin.alexwallet.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class NewsPagingSource (
    private val service: NewsService,
    private val query: String
) : PagingSource<Int, NewsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = service.searchPhotos(query)
            val newsItems = response.results.results
            LoadResult.Page(
                data = newsItems,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.results.results.size) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        TODO("Not yet implemented")
    }
}
