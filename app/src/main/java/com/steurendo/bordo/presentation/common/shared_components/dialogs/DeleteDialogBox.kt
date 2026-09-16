package com.steurendo.bordo.presentation.common.shared_components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.R.string
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun DeleteDialogBox(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    message: String
) {
    DialogBox(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = message)
            CustomButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = string.button_delete_album))
            }
        }
    }
}

@PreviewTheme
@Composable
private fun DeleteDialogBoxPreview() {
    BordoAppTheme {
        DeleteDialogBox(
            onDismiss = {},
            onConfirm = {},
            message = "Do you want to delete album \"Test Album 2\"?"
        )
    }
}