package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.nytmostpopular.MostPopularResult
import ru.slatinin.nytnews.data.nytsections.NytSectionRepository
import ru.slatinin.nytnews.data.nytsections.SectionResult
import ru.slatinin.nytnews.ui.fragments.SectionFragmentArgs
import javax.inject.Inject


@HiltViewModel
class SectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sectionRepository: NytSectionRepository
) : ViewModel() {
    val section: String = SectionFragmentArgs.fromSavedStateHandle(savedStateHandle).sectionName
    private var sectionResult: Flow<PagingData<SectionResult>>? = null

    fun loadSection(): Flow<PagingData<SectionResult>> {
        return if (sectionResult == null) {
            val newResult =
                sectionRepository.loadSectionResultStream(section).cachedIn(viewModelScope)
            sectionResult = newResult
            newResult
        } else {
            sectionResult as Flow<PagingData<SectionResult>>
        }
    }
}