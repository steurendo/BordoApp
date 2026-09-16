package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.NavigationButton
import com.steurendo.bordo.presentation.common.shared_components.NavigationButtonType
import com.steurendo.bordo.presentation.common.shared_components.NavigationTopBar


@Composable
fun TopBar(
    onClickBack: () -> Unit,
    onClickRestore: () -> Unit,
    onClickSave: () -> Unit
) {
    NavigationTopBar(
        title = stringResource(R.string.cropping_title),
        onClickBack = onClickBack,
        onClickNext = onClickSave,
        nextButtonType = NavigationButtonType.Save,
        additionalComponents = {
            NavigationButton(action = onClickRestore, type = NavigationButtonType.Restore)
        }
    )
}


@Preview
@Composable
private fun TopBarPreview() {
    TopBar(onClickBack = {}, onClickRestore = {}, onClickSave = {})
}