package ru.coin.alexwallet.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val service: NewsService,
    private val query: String
) : PagingSource<Int, NewsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {

        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.searchPhotos(query, page - 1)
            val newsItems = response.results.docs
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
