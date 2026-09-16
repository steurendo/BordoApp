package com.steurendo.bordo.app

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BordoApplication : Application() {
    companion object {
        val USING_LEGACY_BLUR: Boolean = Build.VERSION.SDK_INT <= Build.VERSION_CODES.S
        const val MAX_PHOTOS_PER_ALBUM: Int = 20
    }
}