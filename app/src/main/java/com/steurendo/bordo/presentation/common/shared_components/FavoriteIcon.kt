package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.presentation.theme.YellowFav

@Composable
fun FavoriteIcon(modifier: Modifier = Modifier) {
    Icon(
        Icons.TwoTone.Star,
        modifier = modifier,
        tint = YellowFav,
        contentDescription = null
    )
}

@Preview
@Composable
private fun FavoriteIconPreview() {
    FavoriteIcon()
}