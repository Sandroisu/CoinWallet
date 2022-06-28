package ru.slatinin.nytnews.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.models.SectionItem

@Module
@InstallIn(ActivityComponent::class)
class SectionModule {
    @ActivityScoped
    @Provides
    fun getSectionsArray(@ActivityContext context: Context): ArrayList<SectionItem> {
        val sectionStrings = context.resources.getStringArray(R.array.section_names)
        val arr = ArrayList<SectionItem>()
        for (sectionName in sectionStrings) {
            arr.add(SectionItem(sectionName))
        }
        return arr
    }

}