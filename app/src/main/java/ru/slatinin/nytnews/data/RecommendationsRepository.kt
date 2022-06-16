package ru.slatinin.nytnews.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.news.MostPopularResult
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecommendationsRepository @Inject constructor(
    private val newsService: RecommendationService
) {

    fun loadPopularResultStream(type: String): Flow<PagingData<MostPopularResult>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 5,
                initialLoadSize = 10,
                maxSize = 20
            ),
            pagingSourceFactory = { RecommendationsPagingSource(newsService, type) }
        ).flow
    }

}