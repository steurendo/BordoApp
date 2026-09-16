package com.steurendo.bordo.presentation.ui.layoutdefinition

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.steurendo.bordo.app.BordoApplication.Companion.MAX_PHOTOS_PER_ALBUM
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.usecases.album_management.AddPhotosToCurrentAlbumManagementFromUrisUseCase
import com.steurendo.bordo.domain.usecases.album_management.GetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.album_management.RemovePhotoFromCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.album_management.SetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.album_management.SetReferencePhotoToCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.albums.UpsertAlbumUseCase
import com.steurendo.bordo.presentation.mapper.toDomain
import com.steurendo.bordo.presentation.mapper.toUiModel
import com.steurendo.bordo.presentation.navigation.destinations.DefineLayoutDestination
import com.steurendo.bordo.utils.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = DefineLayoutViewModel.Factory::class)
class DefineLayoutViewModel @AssistedInject constructor(
    // Album management
    private val getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase,
    private val setReferencePhotoToCurrentAlbumManagementUseCase: SetReferencePhotoToCurrentAlbumManagementUseCase,
    private val removePhotoFromCurrentAlbumManagementUseCase: RemovePhotoFromCurrentAlbumManagementUseCase,
    private val addPhotosToCurrentAlbumManagementFromUrisUseCase: AddPhotosToCurrentAlbumManagementFromUrisUseCase,
    // Albums
    private val upsertAlbumUseCase: UpsertAlbumUseCase,
    // Params
    @Assisted val navKey: DefineLayoutDestination
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(navKey: DefineLayoutDestination): DefineLayoutViewModel
    }

    private val _uiState = MutableStateFlow(DefineLayoutUiState())
    val uiState: StateFlow<DefineLayoutUiState> = _uiState.asStateFlow()

    init {
        val album: Album = checkNotNull(getCurrentAlbumManagementUseCase.invoke())
        _uiState.update {
            it.copy(
                changeMode = navKey.changeMode,
                album = album.toUiModel(),
                selectedPhotoIndex = navKey.selectedPhotoIndex,
                canChangeReferencePhoto = album.referencePhotoIndex != navKey.selectedPhotoIndex,
                visibleAddPhotosButton = album.photos.size < MAX_PHOTOS_PER_ALBUM,
            )
        }
    }

    fun toggleRemoveDialog(show: Boolean) = _uiState.update { it.copy(showRemoveDialog = show) }

    fun toggleExitDialog(show: Boolean) = _uiState.update { it.copy(showExitDialog = show) }

    fun setReferencePhoto() {
        Logger.i("Setting reference photo to ${_uiState.value.selectedPhotoIndex}", "removePhotoFromNewAlbum")
        setReferencePhotoToCurrentAlbumManagementUseCase(_uiState.value.selectedPhotoIndex)
        val updatedAlbum = getCurrentAlbumManagementUseCase()
        _uiState.update {
            it.copy(
                album = updatedAlbum.toUiModel(),
                canChangeReferencePhoto = false,
            )
        }
    }

    fun setSelectedPhoto(photoIndex: Int) {
        _uiState.update {
            it.copy(
                selectedPhotoIndex = photoIndex,
                canChangeReferencePhoto = photoIndex != it.album.referencePhotoIndex
            )
        }
    }

    fun addPhotosToAlbumFromUris(uriPhotos: List<Uri>) {
        Logger.i("Adding ${uriPhotos.size} photos into the new album", "addPhotosToAlbum")
        addPhotosToCurrentAlbumManagementFromUrisUseCase(uriPhotos = uriPhotos)
        val updatedAlbum = getCurrentAlbumManagementUseCase()
        _uiState.update { it.copy(album = updatedAlbum.toUiModel()) }
    }

    fun removePhotoFromNewAlbum() {
        Logger.i("Removing selected photo from the new album", "removePhotoFromNewAlbum")
        removePhotoFromCurrentAlbumManagementUseCase(_uiState.value.selectedPhotoIndex)
        val updatedAlbum = getCurrentAlbumManagementUseCase()
        _uiState.update {
            val newSelectedIndex = it.selectedPhotoIndex.coerceAtMost(it.album.photos.size - 2)
            return@update it.copy(
                album = updatedAlbum.toUiModel(),
                selectedPhotoIndex = newSelectedIndex,
                canChangeReferencePhoto = newSelectedIndex != it.album.referencePhotoIndex,
                visibleAddPhotosButton = true
            )
        }
    }

    fun passNewAlbum() {
        setCurrentAlbumManagementUseCase.invoke(album = _uiState.value.album.toDomain())
    }

    suspend fun updateAlbum() {
        setCurrentAlbumManagementUseCase.invoke(album = _uiState.value.album.toDomain())
        upsertAlbumUseCase.invoke(album = _uiState.value.album.toDomain())
    }
}