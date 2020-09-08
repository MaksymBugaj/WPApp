package wpa.wp.myapplication.data.db.entity.details


import com.google.gson.annotations.SerializedName

data class SponsoredResults(
    val content: String,
    val imageAuthor: String,
    val imageHeight: String,
    val imageSource: String,
    val imageUrl: String,
    val imageWidth: String,
    val mainColor: String,
    val textColor: String
)