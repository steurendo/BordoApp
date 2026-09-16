package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.NavigationButtonType
import com.steurendo.bordo.presentation.common.shared_components.NavigationTopBar


@Composable
fun TopBar(
    onClickBack: () -> Unit,
    onClickSave: () -> Unit
) {
    NavigationTopBar(
        title = stringResource(id = R.string.effect_selection),
        onClickBack = onClickBack,
        onClickNext = onClickSave,
        nextButtonType = NavigationButtonType.Save
    )
}


@Preview
@Composable
private fun TopBarPreview() {
    TopBar(onClickBack = {}, onClickSave = {})
}