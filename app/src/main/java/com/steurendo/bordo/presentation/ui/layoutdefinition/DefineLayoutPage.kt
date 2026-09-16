package com.steurendo.bordo.presentation.ui.layoutdefinition

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.app.BordoApplication.Companion.MAX_PHOTOS_PER_ALBUM
import com.steurendo.bordo.presentation.common.shared_components.dialogs.DeleteDialogBox
import com.steurendo.bordo.presentation.common.shared_components.dialogs.ExitDialogBox
import kotlinx.coroutines.launch

@Composable
fun DefineLayoutPage(
    onClickBack: () -> Unit,
    onClickNext: (selectedPhotoIndex: Int) -> Unit,
    onClickSave: (albumId: Int, selectedPhotoIndex: Int) -> Unit,
    onClickCrop: (selectedPhotoIndex: Int) -> Unit,
    vm: DefineLayoutViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var uriPhotosToBeAdded by remember { mutableStateOf<List<Uri>?>(null) }

    val photoPickerLauncher = if (uiState.album.photos.size < MAX_PHOTOS_PER_ALBUM - 1)
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
        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    BackHandler {
        if (!uiState.showExitDialog && !uiState.showRemoveDialog) {
            if (uiState.changeMode)
                onClickBack()
            else
                vm.toggleExitDialog(true)
        }
    }

    DefineLayoutTemplate(
        onClickBack = {
            if (uiState.changeMode)
                onClickBack()
            else
                vm.toggleExitDialog(true)
        },
        onClickNext = {
            if (uiState.changeMode) {
                coroutineScope.launch {
                    vm.updateAlbum()
                    onClickSave(uiState.album.id, uiState.selectedPhotoIndex)
                }
            } else {
                vm.passNewAlbum()
                onClickNext(uiState.selectedPhotoIndex)
            }
        },
        onClickAddPhotos = { launchImagePicker() },
        onClickRemovePhoto = { vm.toggleRemoveDialog(true) },
        onClickCropPhoto = {
            vm.passNewAlbum()
            onClickCrop(uiState.selectedPhotoIndex)
        },
        onClickSetReferencePhoto = { vm.setReferencePhoto() },
        onSelectPhoto = { vm.setSelectedPhoto(it) },
        album = uiState.album,
        selectedPhotoIndex = uiState.selectedPhotoIndex,
        canAddPhotos = uiState.visibleAddPhotosButton,
        canSetReferencePhoto = uiState.canChangeReferencePhoto,
        changeMode = uiState.changeMode
    )

    if (uiState.showRemoveDialog)
        DeleteDialogBox(
            onDismiss = { vm.toggleRemoveDialog(false) },
            onConfirm = { vm.removePhotoFromNewAlbum() },
            message = stringResource(id = R.string.msg_remove_photo)
        )

    if (uiState.showExitDialog)
        ExitDialogBox(
            onDismiss = { vm.toggleExitDialog(false) },
            onConfirm = onClickBack
        )
}