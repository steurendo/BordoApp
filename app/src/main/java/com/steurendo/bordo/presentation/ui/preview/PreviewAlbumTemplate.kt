package com.steurendo.bordo.presentation.ui.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.presentation.common.mock.mockAlbum
import com.steurendo.bordo.presentation.common.shared_components.PagerPhotosDisplayer
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.ui.preview.components.AddPhotoFAB
import com.steurendo.bordo.presentation.ui.preview.components.MoreActionMenu
import com.steurendo.bordo.presentation.ui.preview.components.RemovePhotoFAB
import com.steurendo.bordo.presentation.ui.preview.components.TopBar


@Composable
fun PreviewAlbumTemplate(
    onClickHome: () -> Unit,
    onClickRenameAlbum: () -> Unit,
    onClickAddPhotos: () -> Unit,
    onClickRemovePhoto: () -> Unit,
    onClickChangeEffect: () -> Unit,
    onClickChangeReferencePhoto: () -> Unit,
    onClickDeleteAlbum: () -> Unit,
    onChangeCurrentPhoto: (Int) -> Unit,
    album: AlbumUiModel,
    showRemovePhotoButton: Boolean,
    showAddPhotosButton: Boolean,
    currentPhotoIndex: Int
) {
    val showMenu = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(com.steurendo.bordo.R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showRemovePhotoButton)
                    RemovePhotoFAB(onClick = onClickRemovePhoto)
                else
                    Spacer(modifier = Modifier)
                if (showAddPhotosButton)
                    AddPhotoFAB(onClick = onClickAddPhotos)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            TopBar(
                currentAlbumTitle = album.name,
                onClickHome = onClickHome,
                onClickRenameAlbum = onClickRenameAlbum,
                onClickAddPhotos = onClickAddPhotos,
                onClickMore = { showMenu.value = true },
                canAddPhotos = false,
                actionMenu = {
                    MoreActionMenu(
                        onClickChangeEffect = onClickChangeEffect,
                        onClickChangeReferencePhoto = onClickChangeReferencePhoto,
                        onClickDeleteAlbum = onClickDeleteAlbum,
                        onDismiss = { showMenu.value = false },
                        expanded = showMenu.value
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PagerPhotosDisplayer(
                emitCurrentPhotoIndex = onChangeCurrentPhoto,
                album = album,
                initialPage = currentPhotoIndex
            )
        }
    }
}


@PreviewTheme
@Composable
private fun PreviewAlbumPreview() {
    BordoAppTheme {
        PreviewAlbumTemplate(
            onClickRenameAlbum = {},
            onClickDeleteAlbum = {},
            onClickHome = {},
            onClickAddPhotos = {},
            onClickRemovePhoto = {},
            onClickChangeEffect = {},
            onChangeCurrentPhoto = {},
            onClickChangeReferencePhoto = {},
            showAddPhotosButton = true,
            showRemovePhotoButton = true,
            album = mockAlbum,
            currentPhotoIndex = 0,
        )
    }
}