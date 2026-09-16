package com.steurendo.bordo.presentation.ui.home

import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val albums: List<AlbumUiModel> = emptyList(),
        val showDeleteDialog: Boolean = false,
        val showGenerateDialog: Boolean = false,
        val visibleProgressDialog: Boolean = false,
        val visibleSuccessPanel: Boolean = false,
    ) : HomeUiState
}