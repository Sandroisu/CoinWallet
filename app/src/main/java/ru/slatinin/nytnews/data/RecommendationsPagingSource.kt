package ru.slatinin.nytnews.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.slatinin.nytnews.data.news.MostPopularResult

private const val STARTING_PAGE_INDEX = 1
private const val THREE_HOURS = 1000 * 60 * 60 * 3

class RecommendationsPagingSource(
    private val service: RecommendationService,
    private val type: String,
) : PagingSource<Int, MostPopularResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MostPopularResult> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.loadNews(type = type)
            val newsItems = response.results
            LoadResult.Page(
                data = newsItems,
                nextKey = if (page > STARTING_PAGE_INDEX) null else page + 1,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MostPopularResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
