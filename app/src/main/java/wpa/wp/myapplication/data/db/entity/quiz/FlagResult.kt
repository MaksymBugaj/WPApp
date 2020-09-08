package wpa.wp.myapplication.data.db.entity.quiz


import com.google.gson.annotations.SerializedName

data class FlagResult(
    val content: String,
    val flag: String,
    val image: Image,
    val title: String
)