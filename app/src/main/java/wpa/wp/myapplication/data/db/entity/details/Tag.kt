package wpa.wp.myapplication.data.db.entity.details


import com.google.gson.annotations.SerializedName

data class Tag(
    val name: String,
    val primary: Boolean,
    val type: String,
    val uid: Long
)