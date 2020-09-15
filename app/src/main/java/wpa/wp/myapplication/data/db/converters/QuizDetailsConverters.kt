package wpa.wp.myapplication.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import wpa.wp.myapplication.data.db.entity.details.*
import java.io.IOException


class QuizDetailsConverters {

    /*@TypeConverter
    fun toJson(value: Any?): Any {
        val gson = Gson()
        val type = object : TypeToken<Any>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun jsonTo(value: String) :Any {
        if (value == "null")
            return Any()
        return Gson().fromJson(value, Any())
    }*/

    @TypeConverter
    fun categoryListToJson(value: List<Category>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun jsonToCategoryList(value: String) :List<Category> {
            if (value == "null")
                    return emptyList()
            return Gson().fromJson(value, Array<Category>::class.java).toList()
    }

    @TypeConverter
    fun latestResultListToJson(value: List<LatestResult>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToLatestResultList(value: String) :List<LatestResult> {
            if (value == "null")
                    return emptyList()
            return Gson().fromJson(value, Array<LatestResult>::class.java).toList()
    }

    @TypeConverter
    fun questionListToJson(value: List<Question>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToQuestionList(value: String): List<Question> {
        if (value == "null")
            return emptyList()
        return Gson().fromJson(value, Array<Question>::class.java).toList()
    }

    @TypeConverter
    fun rateListToJson(value: List<Rate>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToRateList(value: String): List<Rate> {
        if (value == "null")
            return emptyList()
        return Gson().fromJson(value, Array<Rate>::class.java).toList()
    }

    @TypeConverter
    fun tagListToJson(value: List<Tag>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToTagList(value: String): List<Tag> {
        if (value == "null")
            return emptyList()
        return Gson().fromJson(value, Array<Tag>::class.java).toList()
    }

    @TypeConverter
    fun sponsoredResultsListToJson(value: List<SponsoredResults>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToSponsoredResultsList(value: String): List<SponsoredResults> {
        if (value == "null")
            return emptyList()
        return Gson().fromJson(value, Array<SponsoredResults>::class.java).toList()
    }

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        if (value == "null")
            return emptyMap()
        val mapType = object : TypeToken<Map<String, String>>() {

        }.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String, String>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

}

class NullIntToEmptyAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        return (if (rawType != Int::class.java) {
            null
        } else IntNullAdapter() as TypeAdapter<T>)
    }
}

class IntNullAdapter : TypeAdapter<Int?>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): Int {
        // TODO Auto-generated method stub
        if (reader.peek() === JsonToken.NULL) {
            reader.nextNull()
            return 0
        }
        return reader.nextInt()
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: Int?) {
        // TODO Auto-generated method stub
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}

class NullLongToEmptyAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        return (if (rawType != Long::class.java) {
            null
        } else LongNullAdapter() as TypeAdapter<T>)
    }
}

class LongNullAdapter : TypeAdapter<Long?>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): Long {
        // TODO Auto-generated method stub
        if (reader.peek() === JsonToken.NULL) {
            reader.nextNull()
            return 0
        }
        return reader.nextLong()
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: Long?) {
        // TODO Auto-generated method stub
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}

class NullDoubleToEmptyAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        return (if (rawType != Double::class.java) {
            null
        } else DoubleNullAdapter() as TypeAdapter<T>)
    }
}

class DoubleNullAdapter : TypeAdapter<Double?>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): Double {
        // TODO Auto-generated method stub
        if (reader.peek() === JsonToken.NULL) {
            reader.nextNull()
            return 0.0
        }
        return reader.nextDouble()
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: Double?) {
        // TODO Auto-generated method stub
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}


class NullBooleanToEmptyAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        return (if (rawType != Boolean::class.java) {
            null
        } else BooleanNullAdapter() as TypeAdapter<T>)
    }
}

class BooleanNullAdapter : TypeAdapter<Boolean?>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): Boolean {
        // TODO Auto-generated method stub
        if (reader.peek() === JsonToken.NULL) {
            reader.nextNull()
            return false
        }
        return reader.nextBoolean()
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: Boolean?) {
        // TODO Auto-generated method stub
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}
