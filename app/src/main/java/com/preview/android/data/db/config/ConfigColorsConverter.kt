package com.preview.android.data.db.config

import androidx.room.TypeConverter
import com.preview.android.data.db.config.entities.ConfigColorEntity
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type

class ConfigColorsConverter {

    @TypeConverter
    fun fromConfigColorsList(colors: List<ConfigColorEntity?>?): String? {
        if (colors == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ConfigColorEntity?>?>() {}.type
        return gson.toJson(colors, type)
    }

    @TypeConverter
    fun toConfigColorsList(colors: String?): List<ConfigColorEntity> {
        if (colors == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ConfigColorEntity?>?>() {}.type
        return gson.fromJson(colors, type)
    }
}