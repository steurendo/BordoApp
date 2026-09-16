package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun LoadingPanel(modifier: Modifier = Modifier, message: String? = null) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(R.dimen.shape_extra_large))
        )
        if (message != null) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(R.dimen.padding_medium))
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                text = message
            )
        }
    }
}

@PreviewTheme
@Composable
fun PreviewLoadingPanel() {
    BordoAppTheme {
        LoadingPanel(message = "Loading albums...")
    }
}