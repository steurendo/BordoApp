package com.steurendo.bordo.presentation.ui.preview

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steurendo.bordo.app.BordoApplication.Companion.MAX_PHOTOS_PER_ALBUM
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.usecases.album_management.GetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.album_management.SetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.albums.AddPhotosToAlbumUseCase
import com.steurendo.bordo.domain.usecases.albums.DeleteAlbumUseCase
import com.steurendo.bordo.domain.usecases.albums.GetAlbumUseCase
import com.steurendo.bordo.domain.usecases.albums.RemovePhotoFromAlbumUseCase
import com.steurendo.bordo.domain.usecases.albums.RenameAlbumUseCase
import com.steurendo.bordo.presentation.mapper.toUiModel
import com.steurendo.bordo.presentation.navigation.destinations.PreviewAlbumDestination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PreviewAlbumViewModel.Factory::class)
class PreviewAlbumViewModel @AssistedInject constructor(
    // Album management
    getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase,
    // Albums
    getAlbumUseCase: GetAlbumUseCase,
    private val renameAlbumUseCase: RenameAlbumUseCase,
    private val deleteAlbumUseCase: DeleteAlbumUseCase,
    private val addPhotosToAlbumUseCase: AddPhotosToAlbumUseCase,
    private val removePhotoFromAlbumUseCase: RemovePhotoFromAlbumUseCase,
    // Params
    @Assisted val navKey: PreviewAlbumDestination
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(navKey: PreviewAlbumDestination): PreviewAlbumViewModel
    }


    private val albumFlow: Flow<Album> = getAlbumUseCase.invoke(albumId = navKey.albumId)

    private val _uiState = MutableStateFlow(PreviewAlbumUiState.Success())
    val uiState: StateFlow<PreviewAlbumUiState> = combine(
        _uiState,
        albumFlow
    ) { uiState, album ->
        uiState.copy(
            album = album.toUiModel(),
            showDeletePhotoButton = uiState.currentPhotoIndex != album.referencePhotoIndex,
            showAddPhotosButton = album.photos.size < MAX_PHOTOS_PER_ALBUM
        )
    }
        .catch { PreviewAlbumUiState.Error(message = it.message) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PreviewAlbumUiState.Loading(albumName = getCurrentAlbumManagementUseCase.invoke().name)
        )

    init {
        _uiState.update {
            it.copy(
                currentPhotoIndex = navKey.currentPhotoIndex
            )
        }
    }

    fun toggleDeleteAlbumDialog(value: Boolean) =
        _uiState.update { it.copy(showDeleteAlbumDialog = value) }

    fun togglePhotoDeleteDialog(value: Boolean) =
        _uiState.update { it.copy(showDeletePhotoDialog = value) }

    fun toggleRenameAlbumDialog(value: Boolean) =
        _uiState.update { it.copy(showRenameAlbumDialog = value) }

    fun setCurrentPhoto(photoIndex: Int) =
        _uiState.update { it.copy(currentPhotoIndex = photoIndex) }

    fun deleteAlbum() {
        viewModelScope.launch {
            deleteAlbumUseCase.invoke(albumId = navKey.albumId)
        }
    }

    fun renameAlbum(newName: String) {
        viewModelScope.launch {
            renameAlbumUseCase.invoke(
                albumId = navKey.albumId,
                newName = newName
            )
        }
    }

    fun addPhotosToAlbumFromUris(uriPhotos: List<Uri>) {
        viewModelScope.launch {
            addPhotosToAlbumUseCase.invoke(
                albumId = navKey.albumId,
                uriPhotos = uriPhotos
            )
            _uiState.update {
                it.copy(
                    album = albumFlow.first().toUiModel()
                )
            }
        }
    }

    fun removeCurrentPhotoFromAlbum() {
        val photoIndex = _uiState.value.currentPhotoIndex
        viewModelScope.launch {
            removePhotoFromAlbumUseCase.invoke(albumId = navKey.albumId, photoIndex = photoIndex)
        }
    }

    fun setAlbumToManage() {
        viewModelScope.launch {
            val album = albumFlow.first()
            setCurrentAlbumManagementUseCase.invoke(album)
        }
    }
}