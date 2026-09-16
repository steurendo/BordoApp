package com.steurendo.bordo.presentation.ui.layoutdefinition.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectReferencePhotoEnabled: Boolean,
    onClickRemovePhoto: () -> Unit,
    onClickCropPhoto: () -> Unit,
    onClickSelectReferencePhoto: () -> Unit,
) {
    BottomAppBar(modifier = modifier) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = onClickRemovePhoto,
                enabled = selectReferencePhotoEnabled,
            ) {
                Text(text = stringResource(id = R.string.layout_definition_remove))
            }
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = onClickCropPhoto
            ) {
                Text(text = stringResource(id = R.string.layout_definition_crop))
            }
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = onClickSelectReferencePhoto,
                enabled = selectReferencePhotoEnabled
            ) {
                Text(
                    text = stringResource(id = R.string.layout_definition_set_reference_photo),
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
            selectReferencePhotoEnabled = false,
            onClickRemovePhoto = {},
            onClickCropPhoto = {},
            onClickSelectReferencePhoto = {}
        )
    }
}