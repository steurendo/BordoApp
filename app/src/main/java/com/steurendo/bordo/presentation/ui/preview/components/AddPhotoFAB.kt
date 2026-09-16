package com.steurendo.bordo.presentation.ui.preview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R


@Composable
fun AddPhotoFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Row(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = stringResource(id = R.string.fab_add_photos)
            )
        }
    }
}