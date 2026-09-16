package com.steurendo.bordo.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.steurendo.bordo.data.db.converters.DateConverter
import com.steurendo.bordo.data.db.converters.EffectParametersParserConverter
import com.steurendo.bordo.data.db.converters.PaddingEffectConverter
import com.steurendo.bordo.data.db.converters.PhotoConverter
import com.steurendo.bordo.domain.model.AlbumPhoto
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.presentation.common.shared_modules.EffectParamsParser
import java.util.Date

/*
This file contains the data structures useful to represent an Album inside the DB
 */

@Entity(tableName = "albums")
@TypeConverters(
    DateConverter::class,
    PhotoConverter::class,
    PaddingEffectConverter::class,
    EffectParametersParserConverter::class
)
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val referencePhotoIndex: Int,
    val photos: List<AlbumPhoto>,
    val paddingEffect: PaddingEffect,
    val effectParamsParser: EffectParamsParser,
    val creationDate: Date
)