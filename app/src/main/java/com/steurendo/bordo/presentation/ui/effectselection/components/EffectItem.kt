package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R

@Composable
fun EffectItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    content: @Composable BoxScope.() -> Unit,
    selected: Boolean
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        onClick = onClick,
        border = if (selected) BorderStroke(
            dimensionResource(id = R.dimen.stroke_medium),
            MaterialTheme.colorScheme.primary
        ) else null,
        shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.padding_medium))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge,
                    text = title
                )
                Box(modifier = Modifier.fillMaxSize(), content = content)
            }
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 42.dp, end = 6.dp)
                        .size(24.dp)
                        .background(color = Color.White, shape = CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEffectItem() {
    EffectItem(
        onClick = {},
        modifier = Modifier,
        title = "White/Black",
        selected = true,
        content = {
            Box( // This box is just to emulate an image of the effect
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Gray)
                    .border(
                        BorderStroke(
                            dimensionResource(id = R.dimen.stroke_medium),
                            Color.Black
                        )
                    )
                    .aspectRatio(1.6f)
            )
        }
    )
}