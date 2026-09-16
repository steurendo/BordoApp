package com.steurendo.bordo.presentation.common.shared_components.dialogs.rename_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.R.string
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.common.shared_components.dialogs.DialogBox
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun RenameDialogBox(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    onResetName: () -> Unit,
    uiState: RenameDialogUiState
) {
    DialogBox(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            var offset by remember { mutableFloatStateOf(0f) }

            Text(text = stringResource(id = string.renamedialog_select_name))
            TextField(
                value = uiState.name,
                singleLine = true,
                isError = !uiState.isNameAvailable,
                modifier = Modifier
                    .fillMaxWidth()
                    .scrollable(
                        orientation = Orientation.Horizontal,
                        state = rememberScrollableState {
                            offset += it
                            it
                        }
                    ),
                onValueChange = onNameChange,
                trailingIcon = {
                    if (uiState.name != "") {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable { onResetName() }
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = uiState.defaultName,
                        color = Color.Gray
                    )
                }
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                CustomButton(
                    onClick = {
                        onConfirm(if (uiState.name == "") uiState.defaultName else uiState.name)
                        onDismiss()
                    },
                    enabled = uiState.isNameAvailable
                ) {
                    Text(text = stringResource(id = string.renamedialog_confirm))
                }
            }
        }
    }
}

@PreviewTheme
@Composable
private fun RenameDialogBoxPreview() {
    BordoAppTheme {
        RenameDialogBox(
            onConfirm = {},
            onDismiss = {},
            onNameChange = {},
            onResetName = {},
            uiState = RenameDialogUiState(
                name = "Tentativo",
                isNameAvailable = true,
                defaultName = "2024-09-04"
            )
        )
    }
}