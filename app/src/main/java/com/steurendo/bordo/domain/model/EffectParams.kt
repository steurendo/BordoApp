package com.steurendo.bordo.domain.model

abstract class EffectParams {
    companion object {
        fun init(paddingEffect: PaddingEffect): EffectParams {
            return when (paddingEffect) {
                PaddingEffect.WhiteBlack -> WhiteBlackEffectParams()
                PaddingEffect.Blur -> BlurEffectParams()
            }
        }
    }
}

data class WhiteBlackEffectParams(val isBlack: Boolean = false) : EffectParams()

data class BlurEffectParams(val blurRadius: Float = DEFAULT_BLUR_RADIUS) : EffectParams() {
    companion object {
        const val DEFAULT_BLUR_RADIUS = 0.3f
    }
}