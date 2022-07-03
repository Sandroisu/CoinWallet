package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.slatinin.nytnews.ui.fragments.SectionFragmentArgs
import javax.inject.Inject


@HiltViewModel
class SectionViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    val section : String = SectionFragmentArgs.fromSavedStateHandle(savedStateHandle).sectionName

}