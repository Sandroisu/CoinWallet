package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import ru.slatinin.nytnews.data.models.SectionItem

class SectionsViewModel: ViewModel() {
    val sections : ArrayList<SectionItem>
    init {
        sections = ArrayList()
        sections.add(SectionItem(""))
    }

}