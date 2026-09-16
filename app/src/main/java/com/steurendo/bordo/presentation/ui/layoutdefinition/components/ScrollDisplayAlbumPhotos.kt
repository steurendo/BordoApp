package com.steurendo.bordo.presentation.ui.layoutdefinition.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.mock.mockAlbums
import com.steurendo.bordo.presentation.common.shared_components.FavoriteIcon
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.shared_models.getPhoto


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollDisplayAlbumPhotos(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onClickPhoto: (photoIndex: Int) -> Unit,
    framedPhotos: Boolean = false,
    album: AlbumUiModel,
    selectedPhotoIndex: Int
) {
    LazyRow(
        modifier = modifier,
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        var frameModifier: Modifier = Modifier.aspectRatio(1f)
        if (framedPhotos)
            frameModifier = frameModifier.border(width = 0.dp, color = Color.LightGray)
        items(album.photos.size) { photoIndex ->
            Box(
                modifier = frameModifier.clickable { onClickPhoto(photoIndex) },
                contentAlignment = Alignment.Center
            ) {
                val photo = album.getPhoto(photoIndex)
                AsyncImage(
                    model = photo.uri,
                    placeholder = photo.placeholderId?.let { painterResource(id = it) },
                    alpha = if (photoIndex == selectedPhotoIndex) 0.6f else 1f,
                    contentDescription = null
                )
                if (photoIndex == album.referencePhotoIndex)
                    FavoriteIcon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(dimensionResource(id = R.dimen.padding_small))
                    )
            }
        }
    }
}


@Preview
@Composable
fun PreviewScrollDisplayAlbumPhotos() {
    val album = mockAlbums[0]

    ScrollDisplayAlbumPhotos(
        onClickPhoto = {},
        album = album,
        selectedPhotoIndex = 1,
    )
}