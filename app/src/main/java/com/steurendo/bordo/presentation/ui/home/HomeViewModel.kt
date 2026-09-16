package com.steurendo.bordo.presentation.ui.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.usecases.album_management.SetCurrentAlbumManagementUseCase
import com.steurendo.bordo.domain.usecases.albums.GetAllAlbumsUseCase
import com.steurendo.bordo.domain.usecases.gallery.GenerateAlbumToGalleryUseCase
import com.steurendo.bordo.domain.usecases.io.GetImageDimensionsUseCase
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.mapper.toDomain
import com.steurendo.bordo.presentation.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // IO
    private val getImageDimensionsUseCase: GetImageDimensionsUseCase,
    // Album management
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase,
    getAllAlbumsUseCase: GetAllAlbumsUseCase,
    private val generateAlbumToGalleryUseCase: GenerateAlbumToGalleryUseCase
) : ViewModel() {
    private val albumsFlow = getAllAlbumsUseCase.invoke()

    private val _uiState = MutableStateFlow(HomeUiState.Success())
    val uiState: StateFlow<HomeUiState> = combine(
        _uiState,
        albumsFlow
    ) { uiState, albums ->
        uiState.copy(albums = albums.map(Album::toUiModel))
    }
        .catch { _uiState }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

    fun toggleVisibleGenerateDialog(show: Boolean) {
        _uiState.update { it.copy(showGenerateDialog = show) }
    }

    fun toggleVisibleSuccessPanel(visible: Boolean) =
        _uiState.update { it.copy(visibleSuccessPanel = visible) }

    fun initNewAlbumFromUriPhotos(uriPhotos: List<Uri>): AlbumUiModel {
        return AlbumUiModel(
            photos = uriPhotos.map { uriPhoto ->
                val photoSize = getImageDimensionsUseCase.invoke(uri = uriPhoto)
                return@map AlbumPhotoUiModel(
                    uri = uriPhoto,
                    width = photoSize.width,
                    height = photoSize.height
                )
            }
        )
    }

    fun setAlbumManagement(album: AlbumUiModel) {
        setCurrentAlbumManagementUseCase.invoke(album = album.toDomain())
    }

    fun generateAlbumToGallery(album: AlbumUiModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(visibleProgressDialog = true) }
            withContext(Dispatchers.IO) {
                generateAlbumToGalleryUseCase.invoke(album.toDomain())
            }
            _uiState.update { it.copy(visibleProgressDialog = false, visibleSuccessPanel = true) }
        }
    }
}