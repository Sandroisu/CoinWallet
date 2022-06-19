package ru.slatinin.nytnews.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RssRepository @Inject constructor(
    private val rssReader: RssReader
) {

    fun loadRssResult(url: String): Flow<PagingData<RssReader.Item>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 5,
                initialLoadSize = 10,
                maxSize = 20
            ),
            pagingSourceFactory = { RssPagingSource(rssReader, url = url) }
        ).flow
    }
}