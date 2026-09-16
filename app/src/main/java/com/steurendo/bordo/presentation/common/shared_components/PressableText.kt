package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun PressableText(modifier: Modifier = Modifier, onClick: () -> Unit, text: String) {
    Text(
        modifier = modifier.clickable(onClick = onClick),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primaryContainer)) {
                append(text)
            }
        })
}