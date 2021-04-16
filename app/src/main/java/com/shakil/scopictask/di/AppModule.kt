package com.shakil.scopictask.di

import com.google.gson.Gson
import com.shakil.scopictask.repo.NotesRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideNotesRepo(): NotesRepo {
        return NotesRepo()
    }
}