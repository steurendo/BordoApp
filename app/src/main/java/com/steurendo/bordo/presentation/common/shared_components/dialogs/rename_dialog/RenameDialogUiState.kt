package com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog

data class RenameDialogUiState(
    val name: String = "",
    val defaultName: String = "",
    val isNameAvailable: Boolean = true
)