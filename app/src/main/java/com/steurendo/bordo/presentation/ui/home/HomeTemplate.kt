package com.steurendo.bordo.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.R.string
import com.steurendo.bordo.presentation.common.mock.mockAlbums
import com.steurendo.bordo.presentation.common.shared_components.PressableText
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.ui.home.components.CreateAlbumFAB
import com.steurendo.bordo.presentation.ui.home.components.AlbumsList
import com.steurendo.bordo.presentation.ui.home.components.SuccessPanel
import com.steurendo.bordo.presentation.ui.home.components.TopBar


@Composable
fun HomeTemplate(
    onClickAddAlbum: () -> Unit,
    onClickAlbum: (AlbumUiModel) -> Unit,
    onClickGenerate: (AlbumUiModel) -> Unit,
    albums: List<AlbumUiModel>
) {
    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { CreateAlbumFAB(onClick = onClickAddAlbum) })
    { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (albums.isEmpty())
                HomeEmptyContent(onClickAddAlbum = onClickAddAlbum)
            else
                AlbumsList(
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                        .padding(top = dimensionResource(R.dimen.padding_small)),
                    onClickAlbum = onClickAlbum,
                    onClickGenerate = { onClickGenerate(it) },
                    albums = albums
                )
        }
    }
}

@Composable
fun HomeEmptyContent(
    onClickAddAlbum: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.PhotoLibrary,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = LocalContentColor.current.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Text(text = stringResource(id = string.no_albums))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        PressableText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = string.create_new_album),
            onClick = onClickAddAlbum
        )
    }
}


@PreviewTheme
@Composable
private fun HomeTemplateEmptyPreview() {
    BordoAppTheme {
        HomeTemplate(
            onClickAddAlbum = {},
            onClickAlbum = {},
            onClickGenerate = {},
            albums = listOf()
        )
    }
}

@PreviewTheme
@Composable
private fun HomeTemplatePreview() {
    BordoAppTheme {
        HomeTemplate(
            onClickAddAlbum = {},
            onClickAlbum = {},
            onClickGenerate = {},
            albums = mockAlbums
        )
    }
    SuccessPanel(modifier = Modifier.fillMaxSize())
}