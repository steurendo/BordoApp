package com.steurendo.bordo.presentation.navigation.destinations

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class DefineLayoutDestination(
    val selectedPhotoIndex: Int,
    val changeMode: Boolean,
    val refreshKey: Long = System.currentTimeMillis()
) : NavKey