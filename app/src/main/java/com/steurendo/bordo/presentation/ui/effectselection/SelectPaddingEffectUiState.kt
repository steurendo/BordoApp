package com.steurendo.bordo.presentation.ui.effectselection

import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel

data class SelectPaddingEffectUiState(
    val changeMode: Boolean = false,
    val album: AlbumUiModel = AlbumUiModel(),
    val currentPhotoIndex: Int = 0,
    val visibleRenameDialogBox: Boolean = false
)