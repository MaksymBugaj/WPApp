package wpa.wp.myapplication.data.db.entity.quiz


import com.google.gson.annotations.SerializedName

data class Category(
    val name: String,
    val secondaryCid: String,
    val type: String,
    val uid: Long
)