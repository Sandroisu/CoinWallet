package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.RecommendationsRepository
import ru.slatinin.nytnews.data.news.MostPopularResult
import ru.slatinin.nytnews.storage.AppDatabase
import ru.slatinin.nytnews.storage.CryptoCurrency
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: RecommendationsRepository,
) : ViewModel() {
    private var loadViewsResult: Flow<PagingData<MostPopularResult>>? = null
    private var loadEmailsResult: Flow<PagingData<MostPopularResult>>? = null
    private var loadSharedResult: Flow<PagingData<MostPopularResult>>? = null

    fun loadPopularByViews(type: String): Flow<PagingData<MostPopularResult>> {
        return if (loadViewsResult == null) {
            val newResult = repository.loadPopularResultStream(type).cachedIn(viewModelScope)
            loadViewsResult = newResult
            newResult
        } else {
            loadViewsResult as Flow<PagingData<MostPopularResult>>
        }
    }
    fun loadPopularByEmails(type: String): Flow<PagingData<MostPopularResult>> {
        return if (loadEmailsResult == null) {
            val newResult = repository.loadPopularResultStream(type).cachedIn(viewModelScope)
            loadEmailsResult = newResult
            newResult
        } else {
            loadEmailsResult as Flow<PagingData<MostPopularResult>>
        }
    }
    fun loadPopularByShared(type: String): Flow<PagingData<MostPopularResult>> {
        return if (loadSharedResult == null) {
            val newResult = repository.loadPopularResultStream(type).cachedIn(viewModelScope)
            loadSharedResult = newResult
            newResult
        } else {
            loadSharedResult as Flow<PagingData<MostPopularResult>>
        }
    }
}
