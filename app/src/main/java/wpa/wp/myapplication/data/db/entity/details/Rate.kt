package wpa.wp.myapplication.data.db.entity.details


import com.google.gson.annotations.SerializedName

data class Rate(
    val content: String,
    val from: Int,
    val to: Int
)