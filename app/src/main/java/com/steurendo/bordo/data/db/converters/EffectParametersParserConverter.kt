package com.steurendo.bordo.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.steurendo.bordo.presentation.common.shared_modules.EffectParamsParser

class EffectParametersParserConverter {
    @TypeConverter
    fun toEffectParametersParser(string: String?): EffectParamsParser =
        Gson().fromJson(string, EffectParamsParser::class.java)

    @TypeConverter
    fun fromEffectParametersParser(effectParamsParser: EffectParamsParser): String =
        Gson().toJson(effectParamsParser)
}