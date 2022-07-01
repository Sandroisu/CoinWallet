package ru.slatinin.nytnews.adapters

import ru.slatinin.nytnews.data.models.SectionItem

interface SectionClickListener {
    fun onSectionClick(sectionItem: SectionItem)
}