package com.example.trading21

import com.example.trading21.base.core.util.DefaultDispatcherProvider
import com.example.trading21.base.core.util.DispatcherProvider
import com.example.trading21.base.presentation.navigation.NavigationManager
import com.example.trading21.base.presentation.navigation.NavigationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Singleton
    @Provides
    fun provideNavigationManager(): NavigationManager = NavigationManagerImpl()
}