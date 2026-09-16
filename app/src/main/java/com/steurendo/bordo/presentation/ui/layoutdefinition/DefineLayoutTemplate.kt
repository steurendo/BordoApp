package com.steurendo.bordo.presentation.ui.layoutdefinition

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.mock.mockAlbum
import com.steurendo.bordo.presentation.common.shared_components.FramedPhotoDisplayer
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.shared_models.computedAspectRatio
import com.steurendo.bordo.presentation.common.shared_models.getPhoto
import com.steurendo.bordo.presentation.common.shared_models.getReferencePhoto
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.ui.layoutdefinition.components.MultiFloatingActionButton
import com.steurendo.bordo.presentation.ui.layoutdefinition.components.MultiFloatingActionButtonItem
import com.steurendo.bordo.presentation.ui.layoutdefinition.components.ScrollDisplayAlbumPhotos
import com.steurendo.bordo.presentation.ui.layoutdefinition.components.TopBar


suspend fun LazyListState.animateScrollToItemCentered(index: Int) {
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    if (itemInfo == null) return animateScrollToItem(index)
    val center = layoutInfo.viewportEndOffset / 2
    val itemCenter = itemInfo.offset + itemInfo.size / 2
    animateScrollBy((itemCenter - center).toFloat())
}

@Composable
fun DefineLayoutTemplate(
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    onClickAddPhotos: () -> Unit,
    onClickRemovePhoto: () -> Unit,
    onClickCropPhoto: () -> Unit,
    onClickSetReferencePhoto: () -> Unit,
    onSelectPhoto: (photoIndex: Int) -> Unit,
    album: AlbumUiModel,
    selectedPhotoIndex: Int,
    canAddPhotos: Boolean,
    canSetReferencePhoto: Boolean,
    changeMode: Boolean = false
) {
    var showActionMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                onClickBack = onClickBack,
                onClickNext = onClickNext,
                changeMode = changeMode
            )
        },
        floatingActionButton = {
            MultiFloatingActionButton(
                onAction = { showActionMenu = !showActionMenu },
                isExpanded = showActionMenu,
                items = listOf(
                    MultiFloatingActionButtonItem(
                        action = onClickSetReferencePhoto,
                        icon = Icons.Filled.Star,
                        text = stringResource(R.string.layout_definition_set_reference_photo),
                        visible = canSetReferencePhoto
                    ),
                    MultiFloatingActionButtonItem(
                        action = onClickCropPhoto,
                        icon = Icons.Filled.Crop,
                        text = stringResource(R.string.layout_definition_crop)
                    ),
                    MultiFloatingActionButtonItem(
                        action = onClickRemovePhoto,
                        icon = Icons.Filled.Delete,
                        text = stringResource(R.string.layout_definition_remove),
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        visible = canSetReferencePhoto
                    ),
                    MultiFloatingActionButtonItem(
                        action = onClickAddPhotos,
                        icon = Icons.Filled.Add,
                        text = stringResource(R.string.layout_definition_add),
                        visible = canAddPhotos
                    )
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            DefineLayoutContent(
                modifier = Modifier.fillMaxSize(),
                album = album,
                selectedPhotoIndex = selectedPhotoIndex,
                onSelectPhoto = onSelectPhoto
            )
            if (showActionMenu)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { showActionMenu = false }
                )
        }
    }
}

@Composable
fun DefineLayoutContent(
    modifier: Modifier = Modifier,
    onSelectPhoto: (photoIndex: Int) -> Unit,
    album: AlbumUiModel,
    selectedPhotoIndex: Int
) {
    val scrollingState = rememberLazyListState()

    LaunchedEffect(selectedPhotoIndex, album.referencePhotoIndex) {
        scrollingState.animateScrollToItemCentered(selectedPhotoIndex)
    }

    Column(modifier = modifier) {
        FramedPhotoDisplayer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(Color.LightGray),
            photo = album.getPhoto(selectedPhotoIndex),
            aspectRatio = album.getReferencePhoto().computedAspectRatio,
            paddingEffect = album.paddingEffect,
            paddingEffectParameters = album.effectParameters
        )
        ScrollDisplayAlbumPhotos(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_extra_large))
                .fillMaxHeight(0.65f)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .border(width = 0.5.dp, color = Color.Black)
                .padding(vertical = dimensionResource(R.dimen.padding_extra_small)),
            onClickPhoto = onSelectPhoto,
            album = album,
            framedPhotos = true,
            state = scrollingState,
            selectedPhotoIndex = selectedPhotoIndex
        )
    }
}


@PreviewTheme
@Composable
fun PreviewDefineLayout() {
    val album = mockAlbum

    BordoAppTheme {
        DefineLayoutTemplate(
            onClickBack = {},
            onClickNext = {},
            onClickAddPhotos = {},
            onClickRemovePhoto = {},
            onClickCropPhoto = {},
            onClickSetReferencePhoto = {},
            onSelectPhoto = {},
            album = album,
            selectedPhotoIndex = 0,
            canAddPhotos = false,
            canSetReferencePhoto = false,
            changeMode = false
        )
    }
}

@PreviewTheme
@Composable
fun PreviewDefineLayoutReferencePhoto() {
    val album = mockAlbum

    BordoAppTheme {
        DefineLayoutTemplate(
            onClickBack = {},
            onClickNext = {},
            onClickAddPhotos = {},
            onClickRemovePhoto = {},
            onClickCropPhoto = {},
            onClickSetReferencePhoto = {},
            onSelectPhoto = {},
            album = album,
            selectedPhotoIndex = album.referencePhotoIndex,
            canAddPhotos = false,
            canSetReferencePhoto = false,
            changeMode = false
        )
    }
}