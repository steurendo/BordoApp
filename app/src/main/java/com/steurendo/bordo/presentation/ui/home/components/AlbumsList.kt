package com.steurendo.bordo.presentation.ui.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.mock.mockAlbums


@Composable
fun AlbumsList(
    modifier: Modifier = Modifier,
    onClickAlbum: (AlbumUiModel) -> Unit,
    onClickGenerate: (AlbumUiModel) -> Unit,
    albums: List<AlbumUiModel>
) {
    LazyColumn(modifier = modifier) {
        items(albums) { album ->
            AlbumItem(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.height_albums))
                    .fillMaxWidth(),
                onClickAlbum = { onClickAlbum(album) },
                onClickGenerate = { onClickGenerate(album) },
                album = album
            )
        }
        item { Spacer(modifier = Modifier.padding(64.dp)) }
    }
}


@Preview
@Composable
fun PreviewAlbumsList() {
    AlbumsList(
        onClickAlbum = {},
        onClickGenerate = {},
        albums = mockAlbums
    )
}