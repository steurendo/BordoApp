package com.steurendo.bordo.presentation.common.shared_components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.steurendo.bordo.app.BordoApplication.Companion.USING_LEGACY_BLUR
import com.steurendo.bordo.utils.ImagePadding.RADIUS_PER_SIZE_RATIO
import com.steurendo.bordo.utils.ImagePadding.blur
import com.steurendo.bordo.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.min

@Composable
fun AdaptiveAsyncImage(
    modifier: Modifier = Modifier,
    uri: Uri,
    blurRadiusFactor: Float = 0f,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = null,
    contentDescription: String?
) {
    var model: Any = uri
    var componentSize by remember { mutableIntStateOf(0) }

    if (USING_LEGACY_BLUR) {
        val context = LocalContext.current
        val blurredBitmap by produceState<Bitmap?>(initialValue = null, uri, blurRadiusFactor) {
            value = withContext(Dispatchers.Default) {
                getBitmapFromUri(context, uri).blur(blurRadiusFactor)
            }
        }
        model = blurredBitmap ?: uri
    }
    AsyncImage(
        modifier = modifier.onGloballyPositioned { coords ->
            componentSize = min(coords.size.width, coords.size.height)
        }.let {
            if (USING_LEGACY_BLUR) it else it.blur(
                componentSize.toDp()
                    .times(RADIUS_PER_SIZE_RATIO)
                    .times(blurRadiusFactor)
            )
        },
        model = model,
        placeholder = placeholder,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}

private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    val src = ImageDecoder.createSource(context.contentResolver, uri)
    val bitmap = ImageDecoder.decodeBitmap(src).copy(Bitmap.Config.ARGB_8888, false)
    Logger.d(
        "Bitmap obtained (Uri: ${uri}).\nWidth: ${bitmap.width}; Height: ${bitmap.height}",
        customTag = "getBitmapFromUri"
    )
    return bitmap
}