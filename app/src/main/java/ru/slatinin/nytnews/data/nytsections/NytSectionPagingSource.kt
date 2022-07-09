package ru.slatinin.nytnews.data.nytsections

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.slatinin.nytnews.data.nytapi.NytResult
import ru.slatinin.nytnews.data.nytsections.SectionResult

private const val STARTING_PAGE_INDEX = 1
class NytSectionPagingSource (private val nytSectionsService: NytSectionService,
                              private val section: String,
) : PagingSource<Int, NytResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NytResult> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = nytSectionsService.loadSections(section = section)
            var newsItems = response.results
            if (page == STARTING_PAGE_INDEX) {
                if (newsItems.size >= 10) {
                    newsItems = response.results.subList(0, 10)
                }
            } else {
                if (response.results.size > 10) {
                    var maxLength = response.results.size - 1
                    if (response.results.size >= 20) {
                        maxLength = 20
                    }
                    newsItems = response.results.subList(10, maxLength)
                }
            }
            LoadResult.Page(
                data = newsItems,
                nextKey = if (page > STARTING_PAGE_INDEX) null else page + 1,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, NytResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}