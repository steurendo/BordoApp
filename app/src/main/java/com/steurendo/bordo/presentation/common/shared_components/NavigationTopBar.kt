package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class NavigationButtonType {
    Home,
    Back,
    Next,
    Rename,
    Save,
    Add,
    Delete,
    Restore,
    More
}

@Composable
fun NavigationButton(
    action: () -> Unit,
    type: NavigationButtonType
) {
    IconButton(onClick = action) {
        when (type) {
            NavigationButtonType.Home -> Icon(
                Icons.Filled.Home,
                contentDescription = null
            )

            NavigationButtonType.Next -> Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )

            NavigationButtonType.Save -> Icon(
                Icons.Filled.Check,
                contentDescription = null
            )

            NavigationButtonType.Back -> Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )

            NavigationButtonType.Rename -> Icon(
                Icons.Filled.Edit,
                contentDescription = null
            )

            NavigationButtonType.More -> Icon(
                Icons.Filled.MoreVert,
                contentDescription = null
            )

            NavigationButtonType.Delete -> Icon(
                Icons.Filled.Delete,
                contentDescription = null
            )

            NavigationButtonType.Add -> Icon(
                Icons.Filled.Add,
                contentDescription = null
            )

            NavigationButtonType.Restore -> Icon(
                Icons.Filled.Refresh,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    onClickTitle: () -> Unit = {},
    backButtonType: NavigationButtonType = NavigationButtonType.Back,
    nextButtonType: NavigationButtonType = NavigationButtonType.Next,
    additionalComponents: @Composable () -> Unit = {},
    actionMenu: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.clickable { onClickTitle() },
                text = title,
            )
        },
        navigationIcon = { NavigationButton(type = backButtonType, action = onClickBack) },
        actions = {
            additionalComponents()
            NavigationButton(type = nextButtonType, action = onClickNext)
            actionMenu()
        }
    )
}