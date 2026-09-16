package com.steurendo.bordo.presentation.ui.effectselection.components

import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.app.BordoApplication.Companion.USING_LEGACY_BLUR
import com.steurendo.bordo.utils.ImagePadding.blur

@Composable
fun EffectItemBlurThumbnail() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .aspectRatio(1.6f)
            .border(border = BorderStroke(1.dp, color = Color.Black)),
        contentAlignment = Alignment.Center
    ) {
        BlurredImage(
            modifier = Modifier
                .aspectRatio(1.6f)
                .fillMaxSize()
        )
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.img_ob_2),
            contentDescription = null
        )
    }
}

@Composable
private fun BlurredImage(modifier: Modifier) {
    val painter = if (USING_LEGACY_BLUR) {
        rememberBlurredPainter(R.drawable.img_ob_2)
    } else {
        painterResource(R.drawable.img_ob_2)
    }

    Image(
        modifier = modifier.let { if (USING_LEGACY_BLUR) it else it.blur(radius = 8.dp) },
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = null
    )
}

@Composable
private fun rememberBlurredPainter(
    @DrawableRes drawable: Int
): Painter {
    val resources = LocalResources.current

    return remember(drawable) {
        val original = BitmapFactory.decodeResource(resources, drawable)
        val blurred = original.blur(1f)
        if (original !== blurred) original.recycle()
        return@remember BitmapPainter(blurred.asImageBitmap())
    }
}

@Preview
@Composable
private fun PreviewEffectItemBlurThumbnail() {
    EffectItem(
        onClick = {},
        modifier = Modifier,
        title = "Blur",
        selected = false,
        content = {
            EffectItemBlurThumbnail()
        }
    )
}