package com.vibes.rv.data.converter

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter
import com.vibes.rv.util.Conversion

class UriConverter : Conversion<Uri, String> {
    @TypeConverter
    override fun convert(value: Uri): String = value.toString()

    @TypeConverter
    override fun revert(value: String): Uri = value.toUri()
}