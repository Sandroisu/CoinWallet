package ru.coin.alexwallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.coin.alexwallet.data.viewdata.NewsItem
import ru.coin.alexwallet.data.NewsRepository
import ru.coin.alexwallet.storage.AppDatabase
import ru.coin.alexwallet.storage.CryptoCurrency
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel() {
    private var searchResult: Flow<PagingData<NewsItem>>? = null
    private var cryptoCurrencies: List<CryptoCurrency>? = null
    private var query: String? = null
    fun searchPictures(query: String): Flow<PagingData<NewsItem>> {
        return if (searchResult == null || query != this.query) {
            this.query = query
            val newResult = repository.getSearchResultStream(query).cachedIn(viewModelScope)
            searchResult = newResult
            newResult
        } else {
            return searchResult as Flow<PagingData<NewsItem>>
        }
    }

    suspend fun getCryptoCurrencies(): List<CryptoCurrency>? {
        cryptoCurrencies = AppDatabase.getInstance()?.cryptoCurrencyDao()?.getAll()
        return cryptoCurrencies
    }

}
