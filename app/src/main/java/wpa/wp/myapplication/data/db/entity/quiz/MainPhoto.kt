package wpa.wp.myapplication.data.db.entity.quiz


import com.google.gson.annotations.SerializedName

data class MainPhoto(
    val author: String,
    val height: Int,
    @SerializedName("media_id")
    val mediaId: Long,
    val source: String,
    val title: String,
    val url: String,
    val width: Int
)