package com.steurendo.bordo.presentation.common.shared_components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun ProgressDialogBox(text: String) {
    DialogBox(onDismissRequest = {}) {
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text
            )
        }
    }
}
@PreviewTheme
@Composable
private fun ProgressDialogBoxPreview() {
    BordoAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ProgressDialogBox(
                text = stringResource(id = R.string.progress_creating_album, "Test Album 1")
            )
        }
    }
}