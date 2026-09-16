package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.ui.crop_photo.PhotoCroppingMode


@Composable
fun BottomBar(
    croppingMode: PhotoCroppingMode,
    onChangeCroppingMode: (PhotoCroppingMode) -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = croppingMode != PhotoCroppingMode.ReferencePhoto,
                onClick = { onChangeCroppingMode(PhotoCroppingMode.ReferencePhoto) }) {
                Text(
                    text = stringResource(R.string.cropping_keep_reference_aspect_ratio),
                    textAlign = TextAlign.Center
                )
            }
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = croppingMode != PhotoCroppingMode.FreeTransform,
                onClick = { onChangeCroppingMode(PhotoCroppingMode.FreeTransform) }) {
                Text(
                    text = stringResource(R.string.cropping_free_cropping),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@PreviewTheme
@Composable
private fun BottomBarPreview() {
    BordoAppTheme {
        BottomBar(
            onChangeCroppingMode = {},
            croppingMode = PhotoCroppingMode.ReferencePhoto
        )
    }
}