package ru.slatinin.nytnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.data.RecommendationService
import ru.slatinin.nytnews.data.RssReader
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule {
    @Singleton
    @Provides
    fun provideNewsService(): RecommendationService {
        return RecommendationService.create()
    }

    @Provides
    fun provideRssReader(): RssReader {
        return RssReader()
    }

}
