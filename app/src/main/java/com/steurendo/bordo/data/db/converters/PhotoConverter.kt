package com.steurendo.bordo.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.steurendo.bordo.domain.model.AlbumPhoto
import java.lang.reflect.Type

class PhotoConverter {
    @TypeConverter
    fun toPhoto(string: String?): AlbumPhoto = Gson().fromJson(string, AlbumPhoto::class.java)

    @TypeConverter
    fun fromPhoto(photo: AlbumPhoto?): String = Gson().toJson(photo)

    @TypeConverter
    fun toPhotosList(string: String?): List<AlbumPhoto> {
        val listType: Type = object : TypeToken<List<AlbumPhoto?>?>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromPhotosList(list: List<AlbumPhoto?>?): String = Gson().toJson(list)
}