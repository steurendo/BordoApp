package com.steurendo.bordo.presentation.ui.preview.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R


@Composable
fun MoreActionMenu(
    onClickChangeEffect: () -> Unit,
    onClickChangeReferencePhoto: () -> Unit,
    onClickDeleteAlbum: () -> Unit,
    onDismiss: () -> Unit,
    expanded: Boolean
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.menu_change_effect)) },
            onClick = {
                onClickChangeEffect()
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.menu_change_reference)) },
            onClick = {
                onClickChangeReferencePhoto()
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.menu_delete_album)) },
            onClick = {
                onClickDeleteAlbum()
                onDismiss()
            }
        )
    }
}
