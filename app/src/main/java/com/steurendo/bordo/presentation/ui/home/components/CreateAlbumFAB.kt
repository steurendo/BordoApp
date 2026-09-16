package com.steurendo.bordo.presentation.ui.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun CreateAlbumFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Row(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = stringResource(id = R.string.fab_create_album)
            )
        }
    }
}


@Preview
@Composable
fun PreviewCreateAlbumFAB() {
    BordoAppTheme {
        CreateAlbumFAB(onClick = {})
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewCreateAlbumFABScaffold() {
    BordoAppTheme {
        Scaffold(
            floatingActionButton = {
                CreateAlbumFAB(onClick = {})
            },
            content = {}
        )
    }
}