package ru.slatinin.nytnews.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val STARTING_PAGE_INDEX = 1

class RssPagingSource(
    private val rssReader: RssReader,
    private val url: String
) : PagingSource<Int, RssReader.Item>() {
    override fun getRefreshKey(state: PagingState<Int, RssReader.Item>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RssReader.Item> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return withContext(Dispatchers.IO) {
            try {
                var lastItem = 10;
                var firstItem = 0
                if (rssReader.items.size == 0) {
                    rssReader.RssParser(url)
                }
                if (rssReader.items.size > lastItem * page) {
                    firstItem = lastItem * page - lastItem;
                    lastItem *= page

                } else {
                    lastItem = rssReader.items.size - 1
                }
                val data = ArrayList<RssReader.Item>()
                for (i in firstItem until lastItem) {
                    data.add(rssReader.getItem(i))
                }
                LoadResult.Page(
                    data = data,
                    nextKey = if (page > STARTING_PAGE_INDEX) null else page + 1,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                )
            } catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }
    }
}