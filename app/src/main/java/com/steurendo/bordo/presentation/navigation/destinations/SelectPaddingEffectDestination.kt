package com.steurendo.bordo.presentation.navigation.destinations

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class SelectPaddingEffectDestination(
    val currentPhotoIndex: Int,
    val changeMode: Boolean
) : NavKey