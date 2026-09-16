package com.steurendo.bordo.presentation.navigation.destinations

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class PhotoCroppingDestination(
    val photoIndex: Int,
    val changeMode: Boolean
) : NavKey