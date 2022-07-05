package ru.slatinin.nytnews.data.nytsections

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NytSectionRepository @Inject constructor(
    private val sectionService: NytSectionService
) {

    fun loadSectionResultStream(section: String): Flow<PagingData<SectionResult>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = 5,
                initialLoadSize = 10,
                maxSize = 20
            ),
            pagingSourceFactory = { NytSectionPagingSource(sectionService, section) }
        ).flow
    }

}
