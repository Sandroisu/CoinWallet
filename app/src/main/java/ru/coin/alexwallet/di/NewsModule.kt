package ru.coin.alexwallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.coin.alexwallet.data.NewsService
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
