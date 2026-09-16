package com.steurendo.bordo.presentation.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.mock.mockAlbums
import com.steurendo.bordo.presentation.common.shared_models.getReferencePhoto


@Composable
fun AlbumThumbnail(
    modifier: Modifier = Modifier,
    album: AlbumUiModel
) {
    Card(
        modifier = modifier
            .background(Color.Transparent)
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(dimensionResource(id = R.dimen.stroke_small), Color.LightGray)
    ) {
        AsyncImage(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .aspectRatio(1f),
            model = album.getReferencePhoto().uri,
            placeholder = album.placeholderId?.let { painterResource(id = it) },
            contentDescription = null
        )
    }
}


@Preview(name = "Vertical padding")
@Composable
fun PreviewAlbumThumbnailVertical() {
    AlbumThumbnail(
        album = mockAlbums[0]
    )
}

@Preview(name = "Horizontal padding")
@Composable
fun PreviewAlbumThumbnailHorizontal() {
    AlbumThumbnail(
        album = mockAlbums[1]
    )
}