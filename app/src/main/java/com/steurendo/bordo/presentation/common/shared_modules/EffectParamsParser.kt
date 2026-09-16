package com.steurendo.bordo.presentation.common.shared_modules

import com.google.gson.Gson
import com.steurendo.bordo.domain.model.BlurEffectParams
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.model.WhiteBlackEffectParams


/* Used to convert the effect parameters class into a JSON and vice versa, by dynamically
* keeping also its parameters. */
data class EffectParamsParser(
    val paddingEffect: PaddingEffect,
    val params: String
) {
    companion object {
        fun parseFrom(effectParams: EffectParams): EffectParamsParser {
            return EffectParamsParser(
                paddingEffect = when (effectParams) {
                    is WhiteBlackEffectParams -> PaddingEffect.WhiteBlack
                    is BlurEffectParams -> PaddingEffect.Blur
                    else -> PaddingEffect.WhiteBlack
                },
                params = Gson().toJson(effectParams)
            )
        }

        fun parseTo(parser: EffectParamsParser): EffectParams {
            val paramsType = when (parser.paddingEffect) {
                PaddingEffect.WhiteBlack -> WhiteBlackEffectParams::class
                PaddingEffect.Blur -> BlurEffectParams::class
            }
            return Gson().fromJson(parser.params, paramsType.java)
        }
    }
}