package ru.slatinin.nytnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.data.NewsService
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class NewsModule {
    @Singleton
    @Provides
    fun provideNewsService(): NewsService {
        return NewsService.create()
    }
}
