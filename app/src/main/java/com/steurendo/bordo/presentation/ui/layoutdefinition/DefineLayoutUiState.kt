package com.steurendo.bordo.presentation.ui.layoutdefinition

import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel


data class DefineLayoutUiState(
    val changeMode: Boolean = false,
    val album: AlbumUiModel = AlbumUiModel(),
    val selectedPhotoIndex: Int = 0,
    val canChangeReferencePhoto: Boolean = false,
    val visibleAddPhotosButton: Boolean = false,
    val showRemoveDialog: Boolean = false,
    val showExitDialog: Boolean = false
)