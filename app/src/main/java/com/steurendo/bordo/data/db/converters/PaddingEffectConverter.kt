package com.steurendo.bordo.data.db.converters

import androidx.room.TypeConverter
import com.steurendo.bordo.domain.model.PaddingEffect

class PaddingEffectConverter {
    @TypeConverter
    fun toPaddingEffect(string: String?): PaddingEffect =
        string?.let { PaddingEffect.valueOf(it) } ?: PaddingEffect.WhiteBlack

    @TypeConverter
    fun fromPaddingEffect(paddingEffect: PaddingEffect): String = paddingEffect.name
}