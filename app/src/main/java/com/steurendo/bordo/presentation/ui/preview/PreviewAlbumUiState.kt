package com.steurendo.bordo.presentation.ui.preview

import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel

sealed interface PreviewAlbumUiState {
    data class Loading(val albumName: String) : PreviewAlbumUiState

    data class Success(
        val album: AlbumUiModel = AlbumUiModel(),
        val currentPhotoIndex: Int = 0,
        val showDeleteAlbumDialog: Boolean = false,
        val showDeletePhotoDialog: Boolean = false,
        val showRenameAlbumDialog: Boolean = false,
        val showDeletePhotoButton: Boolean = false,
        val showAddPhotosButton: Boolean = false
    ) : PreviewAlbumUiState

    data class Error(val message: String? = null) : PreviewAlbumUiState
}