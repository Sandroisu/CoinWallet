package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.slatinin.nytnews.data.models.SectionItem
import javax.inject.Inject

class SectionsViewModel(val sections: ArrayList<SectionItem>): ViewModel() {


 class SectionsViewModelFactory @Inject constructor(private val sections: ArrayList<SectionItem>): ViewModelProvider.NewInstanceFactory(){

     @Suppress("UNCHECKED_CAST")
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return SectionsViewModel(sections) as T
     }
 }

}