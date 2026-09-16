package com.steurendo.bordo.di

import android.content.Context
import com.steurendo.bordo.data.db.dao.AlbumDao
import com.steurendo.bordo.data.db.database.BordoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BordoDatabase {
        return BordoDatabase.getDatabase(context)
    }

    @Provides
    fun provideAlbumDao(database: BordoDatabase): AlbumDao = database.albumDao()
}