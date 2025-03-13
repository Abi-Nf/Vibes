package com.vibes.rv.data.converter

import androidx.room.TypeConverter
import com.vibes.rv.util.Conversion
import java.time.Instant

class InstantConverter : Conversion<Instant, Long> {
    @TypeConverter
    override fun convert(value: Instant): Long {
        return value.toEpochMilli()
    }

    @TypeConverter
    override fun revert(value: Long): Instant {
        return Instant.ofEpochMilli(value)
    }
}