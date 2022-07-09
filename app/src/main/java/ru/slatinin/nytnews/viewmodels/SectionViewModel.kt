package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.slatinin.nytnews.data.nytapi.NytResult
import ru.slatinin.nytnews.data.nytsections.NytSectionRepository
import ru.slatinin.nytnews.ui.fragments.SectionFragmentArgs
import javax.inject.Inject


@HiltViewModel
class SectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sectionRepository: NytSectionRepository
) : ViewModel() {
    val section: String = SectionFragmentArgs.fromSavedStateHandle(savedStateHandle).sectionName
    private var sectionResult: Flow<PagingData<NytResult>>? = null

    fun loadSection(): Flow<PagingData<NytResult>> {
        return if (sectionResult == null) {
            val newResult =
                sectionRepository.loadSectionResultStream(getSectionName()).cachedIn(viewModelScope)
            sectionResult = newResult
            newResult
        } else {
            sectionResult as Flow<PagingData<NytResult>>
        }
    }

    private fun getSectionName():String{
        return when(section){
            "World News" -> "world"
            "Business" -> "business"
            "Russia/Europe/USA" -> "us"
            "Sport" -> "sports"
            "Op-ed" -> "opinion"
            "Games" -> "movies"
            else -> {
                "home"
            }
        }
    }
}