package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R

@Composable
fun EffectItemWhiteBlackThumbnail() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .aspectRatio(1.6f)
            .border(border = BorderStroke(1.dp, color = Color.Black)),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.aspectRatio(1.6f)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.White)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Black)
                    .fillMaxSize()
            )
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.img_ob_2),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PreviewEffectItemWhiteBlackThumbnail() {
    EffectItem(
        onClick = {},
        modifier = Modifier,
        title = "White/Black",
        selected = false,
        content = {
            EffectItemWhiteBlackThumbnail()
        }
    )
}