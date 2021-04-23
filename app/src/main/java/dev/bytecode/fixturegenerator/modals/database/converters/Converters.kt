package dev.bytecode.fixturegenerator.modals.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.bytecode.fixturegenerator.modals.Match


class Converters {

    @TypeConverter
    fun fromMatchList(value: List<Match>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Match>>() {}.type
        return gson.toJson(value, type)

    }

    @TypeConverter
    fun toMatchList(value: String): List<Match> {
        val gson = Gson()
        val type = object : TypeToken<List<Match>>() {}.type
        return gson.fromJson(value, type)

    }

}