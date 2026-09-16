package com.steurendo.bordo.presentation.ui.home

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R.string
import com.steurendo.bordo.app.BordoApplication.Companion.MAX_PHOTOS_PER_ALBUM
import com.steurendo.bordo.presentation.common.shared_components.LoadingScreenContent
import com.steurendo.bordo.presentation.common.shared_components.dialogs.GenerateAlbumDialogBox
import com.steurendo.bordo.presentation.common.shared_components.dialogs.ProgressDialogBox
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.ui.home.components.SplashSuccessPanel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HomePage(
    onClickNewAlbum: () -> Unit,
    onPreviewAlbum: (album: AlbumUiModel) -> Unit,
    vm: HomeViewModel
) {
    val currentUiState by vm.uiState.collectAsState()

    if (currentUiState is HomeUiState.Loading) return LoadingScreenContent(
        message = stringResource(
            string.loading_albums_message
        )
    )

    val uiState = currentUiState as HomeUiState.Success
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = MAX_PHOTOS_PER_ALBUM)
    ) { photos ->
        if (photos.isNotEmpty()) {
            val newAlbum = vm.initNewAlbumFromUriPhotos(uriPhotos = photos)
            vm.setAlbumManagement(album = newAlbum)
            onClickNewAlbum()
        }
    }

    fun launchImagePicker() {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    var albumToGenerate by remember { mutableStateOf(AlbumUiModel()) }

    HomeTemplate(
        onClickAddAlbum = { launchImagePicker() },
        onClickAlbum = {
            vm.setAlbumManagement(it)
            onPreviewAlbum(it)
        },
        onClickGenerate = {
            vm.toggleVisibleGenerateDialog(true)
            albumToGenerate = it
        },
        albums = uiState.albums
    )

    if (uiState.showGenerateDialog) {
        GenerateAlbumDialogBox(
            onDismiss = { vm.toggleVisibleGenerateDialog(false) },
            onConfirm = {
                vm.toggleVisibleGenerateDialog(false)
                vm.generateAlbumToGallery(albumToGenerate)
            },
            message = stringResource(id = string.msg_generate_album, albumToGenerate.name)
        )
    }

    if (uiState.visibleProgressDialog)
        ProgressDialogBox(
            text = stringResource(
                id = string.progress_generating_album,
                albumToGenerate.name
            )
        )
    if (uiState.visibleSuccessPanel) SplashSuccessPanel(onFinished = {
        vm.toggleVisibleSuccessPanel(false)
    })
    DoubleBackPressedHandler(onDoubleBack = { (context as Activity).finishAndRemoveTask() })
}

@Composable
fun DoubleBackPressedHandler(onDoubleBack: () -> Unit) {
    val context = LocalContext.current
    var isBackPressed by remember { mutableStateOf(false) }
    val messageExit = stringResource(string.app_double_back_alert)

    LaunchedEffect(key1 = isBackPressed) {
        if (isBackPressed) {
            delay(2000.milliseconds)
            isBackPressed = false
        }
    }

    BackHandler {
        if (isBackPressed)
            onDoubleBack()
        else {
            isBackPressed = true
            Toast.makeText(
                context,
                messageExit,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}