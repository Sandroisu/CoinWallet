package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.nytmostpopular.NytMostPopularRepository
import ru.slatinin.nytnews.data.RssRepository
import ru.slatinin.nytnews.data.nytmostpopular.MostPopularResult
import ru.slatinin.nytnews.data.RssReader
import ru.slatinin.nytnews.data.nytapi.NytResult
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repositoryMost: NytMostPopularRepository,
    private val rssRepository: RssRepository,
    private val euroRssRepository: RssRepository
) : ViewModel() {
    private var loadViewsResult: Flow<PagingData<NytResult>>? = null
    private var rtRssItemResult: Flow<PagingData<RssReader.Item>>? = null
    private var euroRssItemResult: Flow<PagingData<RssReader.Item>>? = null

    fun loadPopularByViews(type: String): Flow<PagingData<NytResult>> {
        return if (loadViewsResult == null) {
            val newResult = repositoryMost.loadPopularResultStream(type).cachedIn(viewModelScope)
            loadViewsResult = newResult
            newResult
        } else {
            loadViewsResult as Flow<PagingData<NytResult>>
        }
    }

    fun loadRtRssFeed(url: String): Flow<PagingData<RssReader.Item>> {
        return if (rtRssItemResult == null) {
            val rssResult = rssRepository.loadRssResult(url = url).cachedIn(viewModelScope)
            rtRssItemResult = rssResult
            rssResult
        } else {
            rtRssItemResult as Flow<PagingData<RssReader.Item>>
        }
    }

    fun loadEuroRssFeed(url: String): Flow<PagingData<RssReader.Item>> {
        return if (euroRssItemResult == null) {
            val rssResult = euroRssRepository.loadRssResult(url = url).cachedIn(viewModelScope)
            euroRssItemResult = rssResult
            rssResult
        } else {
            euroRssItemResult as Flow<PagingData<RssReader.Item>>
        }
    }

    fun nullAllResults() {
        rtRssItemResult = null
        euroRssItemResult = null
        loadViewsResult = null
    }
}
