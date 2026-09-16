package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R

@Composable
fun PhotosIndicatorNumber(
    modifier: Modifier = Modifier,
    photoNumber: Int,
    photosCount: Int,
    isReferencePhotoIndex: Boolean
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = Color.Black.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_small))) {
            if (isReferencePhotoIndex)
                FavoriteIcon(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_extra_small)))
            else
                Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
            Text(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(vertical = dimensionResource(id = R.dimen.padding_extra_small))
                    .padding(end = dimensionResource(id = R.dimen.padding_small)),
                color = Color.White,
                text = "$photoNumber/$photosCount"
            )
        }
    }
}

@Preview
@Composable
fun PhotosIndicatorNumberPreview() {
    PhotosIndicatorNumber(photoNumber = 2, photosCount = 10, isReferencePhotoIndex = false)
}

@Preview
@Composable
fun PhotosIndicatorNumberPreviewReferencePhoto() {
    PhotosIndicatorNumber(photoNumber = 3, photosCount = 10, isReferencePhotoIndex = true)
}