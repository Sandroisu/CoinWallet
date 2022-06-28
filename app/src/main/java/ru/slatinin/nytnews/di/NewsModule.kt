package ru.slatinin.nytnews.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.NytPopularService
import ru.slatinin.nytnews.data.RssReader
import ru.slatinin.nytnews.data.models.SectionItem
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule() {


    @Singleton
    @Provides
    fun provideNewsService(): NytPopularService {
        return NytPopularService.create()
    }

    @Provides
    fun provideRssReader(): RssReader {
        return RssReader()
    }



}
