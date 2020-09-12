package wpa.wp.myapplication.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import wpa.wp.myapplication.data.db.entity.quiz.Category
import wpa.wp.myapplication.data.db.entity.quiz.FlagResult
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Tag

class QuizConverters {

    @TypeConverter
    fun itemListToJson(value: List<Item>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToItemList(value: String) = Gson().fromJson(value, Array<Item>::class.java).toList()

    @TypeConverter
    fun quizCategoryListToJson(value: List<Category>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun jsonToQuizCategoryList(value: String) = Gson().fromJson(value, Array<Category>::class.java).toList()

    @TypeConverter
    fun quizTagListToJson(value: List<Tag>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToQuizTagList(value: String) : List<Tag>{
        if (value == "null")
            return emptyList()
        return Gson().fromJson(value, Array<Tag>::class.java).toList()
    }

        @TypeConverter
        fun quizFlagListToJson(value: List<FlagResult>?): String = Gson().toJson(value)

        @TypeConverter
        fun jsonToQuizFlagList(value: String?) : List<FlagResult> {
            if (value == "null"){
                return emptyList()
            } else {
                val listType = object : TypeToken<List<FlagResult?>?>() {}.type
                return Gson().fromJson(value, listType)
            }
        }
}