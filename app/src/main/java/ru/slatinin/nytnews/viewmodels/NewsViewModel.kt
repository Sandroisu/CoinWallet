package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.RecommendationsRepository
import ru.slatinin.nytnews.data.RssRepository
import ru.slatinin.nytnews.data.news.MostPopularResult
import ru.slatinin.nytnews.data.RssReader
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: RecommendationsRepository,
    private val rssRepository: RssRepository,
    private val euroRssRepository: RssRepository
) : ViewModel() {
    private var loadViewsResult: Flow<PagingData<MostPopularResult>>? = null
    private var rtRssItemResult: Flow<PagingData<RssReader.Item>>? = null
    private var euroRssItemResult: Flow<PagingData<RssReader.Item>>? = null

    fun loadPopularByViews(type: String): Flow<PagingData<MostPopularResult>> {
        return if (loadViewsResult == null) {
            val newResult = repository.loadPopularResultStream(type).cachedIn(viewModelScope)
            loadViewsResult = newResult
            newResult
        } else {
            loadViewsResult as Flow<PagingData<MostPopularResult>>
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
}
