package com.steurendo.bordo.presentation.common.shared_components.dialogs

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

/*
Base component for a Dialog Box
*/

@Composable
fun DialogBox(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = MaterialTheme.shapes.small,
            content = { content() })
    }
}