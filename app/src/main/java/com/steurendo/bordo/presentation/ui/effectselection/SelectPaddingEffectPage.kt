package com.steurendo.bordo.presentation.ui.effectselection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog.RenameDialogBox
import com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog.RenameDialogViewModel
import kotlinx.coroutines.launch

@Composable
fun SelectPaddingEffectPage(
    onClickBack: () -> Unit,
    onClickCreate: () -> Unit,
    onClickSave: (albumId: Int, currentPhotoIndex: Int) -> Unit,
    vm: SelectPaddingEffectViewModel,
    renameVm: RenameDialogViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val renameUiState by renameVm.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    SelectPaddingEffectTemplate(
        onClickBack = onClickBack,
        onClickSave = {
            if (uiState.changeMode) {
                coroutineScope.launch {
                    vm.updateAlbum()
                    onClickSave(uiState.album.id, uiState.currentPhotoIndex)
                }
            } else
                vm.toggleVisibleSaveDialog(true)
        },
        onEffectSelected = vm::setPaddingEffect,
        onEffectParametersSet = vm::setEffectParameters,
        album = uiState.album,
        currentPhotoIndex = uiState.currentPhotoIndex
    )

    if (uiState.visibleRenameDialogBox) {
        RenameDialogBox(
            uiState = renameUiState,
            onNameChange = { renameVm.setName(it) },
            onResetName = { renameVm.resetName() },
            onConfirm = { newAlbumName ->
                vm.toggleVisibleSaveDialog(false)
                vm.setAlbumName(newAlbumName)
                vm.upsertNewAlbum()
                onClickCreate()
            },
            onDismiss = { vm.toggleVisibleSaveDialog(false) }
        )
    }
}