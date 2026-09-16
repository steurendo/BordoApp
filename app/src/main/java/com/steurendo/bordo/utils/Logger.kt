package com.steurendo.bordo.utils

import android.util.Log

class Logger {
    companion object {
        private var TAG = "logger"
        private var previous_TAG = "logger"
        private const val LOGGING_ENABLED: Boolean = true

        fun setTag(tag: String) {
            previous_TAG = TAG
            TAG = tag
        }

        fun restoreTag() {
            TAG = previous_TAG
        }

        fun d(msg: Any?, customTag: String? = null) {
            val tag = customTag ?: TAG
            if (LOGGING_ENABLED)
                Log.d(tag, "" + msg)
        }

        fun e(msg: Any?, customTag: String? = null) {
            val tag = customTag ?: TAG
            if (LOGGING_ENABLED)
                Log.e(tag, "" + msg)
        }

        fun i(msg: Any?, customTag: String? = null) {
            val tag = customTag ?: TAG
            if (LOGGING_ENABLED)
                Log.i(tag, "" + msg)
        }
    }
}
