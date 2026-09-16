package com.steurendo.bordo.presentation.ui.preview

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.app.BordoApplication.Companion.MAX_PHOTOS_PER_ALBUM
import com.steurendo.bordo.presentation.common.shared_components.LoadingScreenContent
import com.steurendo.bordo.presentation.common.shared_components.dialogs.DeleteDialogBox
import com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog.RenameDialogBox
import com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog.RenameDialogViewModel

@Composable
fun PreviewAlbumPage(
    onClickHome: () -> Unit,
    onClickChangeReferencePhoto: (currentPhotoIndex: Int) -> Unit,
    onClickChangeEffect: (currentPhotoIndex: Int) -> Unit,
    vm: PreviewAlbumViewModel,
    renameVm: RenameDialogViewModel
) {
    val currentUiState by vm.uiState.collectAsState()
    if (currentUiState is PreviewAlbumUiState.Loading) {
        val uiState = currentUiState as PreviewAlbumUiState.Loading
        LoadingScreenContent(
            message = stringResource(
                R.string.loading_album_message,
                uiState.albumName
            )
        )
        return
    }

    val uiState = currentUiState as PreviewAlbumUiState.Success
    val renameUiState by renameVm.uiState.collectAsState()
    var uriPhotosToBeAdded by remember { mutableStateOf<List<Uri>?>(null) }
    val launcher = if (uiState.album.photos.size <= MAX_PHOTOS_PER_ALBUM - 2)
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                maxItems = (MAX_PHOTOS_PER_ALBUM - uiState.album.photos.size).coerceAtLeast(2)
            )
        ) { uriPhotosToBeAdded = it }
    else
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriPhoto ->
            uriPhotosToBeAdded = uriPhoto?.let { listOf(it) }
        }

    LaunchedEffect(uriPhotosToBeAdded) {
        val uriPhotos = uriPhotosToBeAdded
        if (uriPhotos != null) {
            vm.addPhotosToAlbumFromUris(uriPhotos = uriPhotos)
            uriPhotosToBeAdded = null
        }
    }

    fun launchImagePicker() {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    PreviewAlbumTemplate(
        onClickHome = onClickHome,
        onClickRenameAlbum = { vm.toggleRenameAlbumDialog(true) },
        onClickAddPhotos = { launchImagePicker() },
        onClickRemovePhoto = { vm.togglePhotoDeleteDialog(true) },
        onClickChangeEffect = {
            vm.setAlbumToManage()
            onClickChangeEffect(uiState.currentPhotoIndex)
        },
        onClickChangeReferencePhoto = {
            vm.setAlbumToManage()
            onClickChangeReferencePhoto(uiState.currentPhotoIndex)
        },
        onClickDeleteAlbum = { vm.toggleDeleteAlbumDialog(true) },
        onChangeCurrentPhoto = { vm.setCurrentPhoto(it) },
        album = uiState.album,
        showRemovePhotoButton = uiState.showDeletePhotoButton,
        showAddPhotosButton = uiState.showAddPhotosButton,
        currentPhotoIndex = uiState.currentPhotoIndex
    )

    if (uiState.showDeletePhotoDialog) {
        DeleteDialogBox(
            onDismiss = { vm.togglePhotoDeleteDialog(false) },
            onConfirm = { vm.removeCurrentPhotoFromAlbum() },
            message = stringResource(id = R.string.delete_current_photo)
        )
    }
    if (uiState.showDeleteAlbumDialog) {
        DeleteDialogBox(
            onDismiss = { vm.toggleDeleteAlbumDialog(false) },
            onConfirm = {
                vm.deleteAlbum()
                onClickHome()
            },
            message = stringResource(id = R.string.msg_delete_album, uiState.album.name)
        )
    }
    if (uiState.showRenameAlbumDialog) {
        RenameDialogBox(
            onConfirm = {
                if (it != "") {
                    vm.renameAlbum(it)
                    renameVm.setDefaultName(it)
                    renameVm.resetName()
                }
            },
            onDismiss = { vm.toggleRenameAlbumDialog(false) },
            onNameChange = { renameVm.setName(it) },
            onResetName = { renameVm.resetName() },
            uiState = renameUiState
        )
    }
}