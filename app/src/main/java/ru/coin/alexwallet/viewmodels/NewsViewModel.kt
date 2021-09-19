package ru.coin.alexwallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.coin.alexwallet.data.News
import ru.coin.alexwallet.data.NewsItem
import ru.coin.alexwallet.data.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel  @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<NewsItem>>? = null

    fun searchPictures(): Flow<PagingData<NewsItem>> {
        val newResult: Flow<PagingData<NewsItem>> =
            repository.getSearchResultStream().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
