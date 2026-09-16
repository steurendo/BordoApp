package com.steurendo.bordo.presentation.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.R.drawable
import com.steurendo.bordo.presentation.common.mock.mockAlbum
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    onClickAlbum: () -> Unit,
    onClickGenerate: () -> Unit,
    album: AlbumUiModel
) {
    Row(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Card(
            onClick = onClickAlbum,
            shape = MaterialTheme.shapes.small
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row {
                    AlbumThumbnail(
                        album = album,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                            text = album.name
                        )
                    }
                }
                CustomButton(
                    onClick = onClickGenerate,
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                    cornerRadius = 20f
                ) {
                    Icon(
                        painterResource(drawable.baseline_download_24),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@PreviewTheme
@Composable
private fun AlbumContainerPreview() {
    BordoAppTheme {
        AlbumItem(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.height_albums))
                .fillMaxWidth(),
            onClickAlbum = {},
            onClickGenerate = {},
            album = mockAlbum
        )
    }
}