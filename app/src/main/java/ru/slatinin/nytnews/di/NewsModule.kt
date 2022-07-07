package ru.slatinin.nytnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.data.nytmostpopular.NytMostPopularService
import ru.slatinin.nytnews.data.RssReader
import ru.slatinin.nytnews.data.nytsections.NytSectionService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule() {


    @Singleton
    @Provides
    fun provideNewsService(): NytMostPopularService {
        return NytMostPopularService.create()
    }

    @Provides
    fun provideRssReader(): RssReader {
        return RssReader()
    }

    @Provides
    fun getNytSectionService(): NytSectionService {
        return NytSectionService.create()
    }


}
