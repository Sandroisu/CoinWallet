package ru.slatinin.nytnews.data.nytmostpopular

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NytMostPopularRepository @Inject constructor(
    private val newsServiceMost: NytMostPopularService
) {

    fun loadPopularResultStream(type: String): Flow<PagingData<MostPopularResult>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 5,
                initialLoadSize = 10,
                maxSize = 20
            ),
            pagingSourceFactory = { NytMostPopularPagingSource(newsServiceMost, type) }
        ).flow
    }

}