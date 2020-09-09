package wpa.wp.myapplication.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import wpa.wp.myapplication.data.db.entity.details.*
import wpa.wp.myapplication.data.db.entity.quiz.Item

class Converters {

    @TypeConverter
    fun itemListToJson(value: List<Item>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToItemList(value: String) = Gson().fromJson(value, Array<Item>::class.java).toList()

    @TypeConverter
    fun categoryListToJson(value: List<Category>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToCategoryList(value: String) = Gson().fromJson(value, Array<Category>::class.java).toList()

    @TypeConverter
    fun latestResultListToJson(value: List<LatestResult>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToLatestResultList(value: String) = Gson().fromJson(value, Array<LatestResult>::class.java).toList()

    @TypeConverter
    fun questionListToJson(value: List<Question>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToQuestionList(value: String) = Gson().fromJson(value, Array<Question>::class.java).toList()

    @TypeConverter
    fun rateListToJson(value: List<Rate>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToRateList(value: String) = Gson().fromJson(value, Array<Rate>::class.java).toList()

    @TypeConverter
    fun tagListToJson(value: List<Tag>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToTagList(value: String) = Gson().fromJson(value, Array<Tag>::class.java).toList()
}