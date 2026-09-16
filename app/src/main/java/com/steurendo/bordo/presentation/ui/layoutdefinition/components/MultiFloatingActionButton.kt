package com.steurendo.bordo.presentation.ui.layoutdefinition.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.PreviewTheme


private const val ARROW_ROTATION_DURATION = 200
private const val ITEM_FADE_DURATION = 100
private const val ITEM_FADE_STAGGER = 30
private val ITEM_SHADOW_ROOM = 24.dp

data class MultiFloatingActionButtonItem(
    val action: () -> Unit,
    val icon: ImageVector,
    val text: String,
    val containerColor: Color? = null,
    val visible: Boolean = true
)

@Composable
fun MultiFloatingActionButton(
    onAction: () -> Unit,
    isExpanded: Boolean,
    items: List<MultiFloatingActionButtonItem>
) {
    val visibleItems = items.filter(MultiFloatingActionButtonItem::visible)
    val transition = updateTransition(targetState = isExpanded, label = "expansion")
    val arrowRotation by transition.animateFloat(
        transitionSpec = { tween(durationMillis = ARROW_ROTATION_DURATION) },
        label = "arrowRotation"
    ) { expanded -> if (expanded) 180f else 0f }

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.End
    ) {
        if (transition.targetState || transition.currentState)
            visibleItems
                .forEachIndexed { index, item ->
                    key(index) {
                        val fabItemAlpha by transition.animateFloat(
                            transitionSpec = {
                                tween(
                                    durationMillis = ITEM_FADE_DURATION,
                                    delayMillis = ITEM_FADE_STAGGER * if (targetState)
                                        visibleItems.lastIndex - index
                                    else
                                        index
                                )
                            },
                            label = "itemAlpha"
                        ) { expanded -> if (expanded) 1f else 0f }

                        FloatingActionButton(
                            modifier = Modifier.fadeWithoutClippingShadow(
                                alpha = { fabItemAlpha },
                                shadowRoom = ITEM_SHADOW_ROOM
                            ),
                            onClick = item.action
                        ) {
                            Row(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))) {
                                Icon(item.icon, contentDescription = null)
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                                Text(
                                    modifier = Modifier.align(Alignment.Bottom),
                                    text = item.text
                                )
                            }
                        }
                    }
                }
        FloatingActionButton(onClick = onAction) {
            Icon(
                modifier = Modifier.rotate(arrowRotation),
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = null
            )
        }
    }
}


private fun Modifier.fadeWithoutClippingShadow(alpha: () -> Float, shadowRoom: Dp): Modifier = this
    .layout { measurable, constraints ->
        val room = shadowRoom.roundToPx()
        val placeable = measurable.measure(constraints.offset(2 * room, 2 * room))
        layout(
            (placeable.width - 2 * room).coerceAtLeast(0),
            (placeable.height - 2 * room).coerceAtLeast(0)
        ) {
            placeable.place(-room, -room)
        }
    }
    .graphicsLayer { this.alpha = alpha() }
    .padding(shadowRoom)


@PreviewTheme
@Composable
fun PreviewMultiFloatingActionButton() {
    MultiFloatingActionButton(
        onAction = {},
        isExpanded = false,
        items = listOf()
    )
}

@PreviewTheme
@Composable
fun PreviewMultiFloatingActionButtonExpanded() {
    MultiFloatingActionButton(
        onAction = {},
        isExpanded = true,
        items = listOf(
            MultiFloatingActionButtonItem(
                action = {},
                icon = Icons.Filled.Star,
                text = stringResource(R.string.layout_definition_set_reference_photo),
            ),
            MultiFloatingActionButtonItem(
                action = {},
                icon = Icons.Filled.Crop,
                text = stringResource(R.string.layout_definition_crop)
            ),
            MultiFloatingActionButtonItem(
                action = {},
                icon = Icons.Filled.Delete,
                text = stringResource(R.string.layout_definition_remove),
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            MultiFloatingActionButtonItem(
                action = {},
                icon = Icons.Filled.Add,
                text = stringResource(R.string.layout_definition_add),
            )
        )
    )
}