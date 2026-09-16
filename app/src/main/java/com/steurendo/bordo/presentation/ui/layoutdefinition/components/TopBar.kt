package com.steurendo.bordo.presentation.ui.layoutdefinition.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.NavigationButton
import com.steurendo.bordo.presentation.common.shared_components.NavigationButtonType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    changeMode: Boolean
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.define_layout)) },
        navigationIcon = {
            NavigationButton(
                type = NavigationButtonType.Back,
                action = onClickBack
            )
        },
        actions = {
            if (changeMode)
                NavigationButton(action = onClickNext, type = NavigationButtonType.Save)
            else
                TextButton(onClick = onClickNext) { Text(stringResource(R.string.top_bar_next_button)) }
        }
    )
}


@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        onClickBack = {},
        onClickNext = {},
        changeMode = false
    )
}

@Preview
@Composable
private fun TopBarPreviewChangeMode() {
    TopBar(
        onClickBack = {},
        onClickNext = {},
        changeMode = true
    )
}