package com.steurendo.bordo.di

import com.steurendo.bordo.data.repository.ImplAlbumManagementRepository
import com.steurendo.bordo.data.repository.ImplGalleryRepository
import com.steurendo.bordo.data.repository.ImplIORepository
import com.steurendo.bordo.data.repository.OfflineAlbumsRepository
import com.steurendo.bordo.domain.repository.AlbumManagementRepository
import com.steurendo.bordo.domain.repository.AlbumsRepository
import com.steurendo.bordo.domain.repository.GalleryRepository
import com.steurendo.bordo.domain.repository.IORepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAlbumsRepository(impl: OfflineAlbumsRepository): AlbumsRepository

    @Binds
    @Singleton
    abstract fun bindAlbumManagementRepository(impl: ImplAlbumManagementRepository): AlbumManagementRepository

    @Binds
    @Singleton
    abstract fun bindIORepository(impl: ImplIORepository): IORepository

    @Binds
    @Singleton
    abstract fun bindGalleryRepository(impl: ImplGalleryRepository): GalleryRepository
}