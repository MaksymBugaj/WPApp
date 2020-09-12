package wpa.wp.myapplication.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import wpa.wp.myapplication.data.db.entity.details.*


class QuizDetailsConverters {

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

}