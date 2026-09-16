package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = null
        )
        Image(
            painterResource(R.drawable.logo_title),
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_large))
                .fillMaxWidth()
                .aspectRatio(3f),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppLogoPreview() {
    BordoAppTheme {
        FullScreen {
            AppLogo(modifier = Modifier.fillMaxWidth(fraction = 0.6f))
        }
    }
}