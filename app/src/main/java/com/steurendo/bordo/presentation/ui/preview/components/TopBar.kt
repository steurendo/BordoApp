package com.steurendo.bordo.presentation.ui.preview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.presentation.common.shared_components.NavigationButton
import com.steurendo.bordo.presentation.common.shared_components.NavigationButtonType
import com.steurendo.bordo.presentation.common.shared_components.NavigationTopBar


@Composable
fun TopBar(
    currentAlbumTitle: String,
    onClickHome: () -> Unit,
    onClickRenameAlbum: () -> Unit,
    onClickAddPhotos: () -> Unit,
    onClickMore: () -> Unit,
    canAddPhotos: Boolean,
    actionMenu: @Composable () -> Unit
) {
    NavigationTopBar(
        title = currentAlbumTitle,
        onClickBack = onClickHome,
        onClickNext = onClickMore,
        onClickTitle = onClickRenameAlbum,
        backButtonType = NavigationButtonType.Back,
        nextButtonType = NavigationButtonType.More,
        actionMenu = actionMenu,
        additionalComponents = {
            if (canAddPhotos)
                NavigationButton(action = onClickAddPhotos, type = NavigationButtonType.Add)
        }
    )
}


@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        currentAlbumTitle = "Sip sip",
        onClickHome = {},
        onClickRenameAlbum = {},
        onClickAddPhotos = {},
        onClickMore = {},
        canAddPhotos = true,
        actionMenu = {
            MoreActionMenu(
                onClickChangeEffect = {},
                onClickChangeReferencePhoto = {},
                onClickDeleteAlbum = {},
                onDismiss = {},
                expanded = false
            )
        }
    )
}