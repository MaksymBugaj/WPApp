package wpa.wp.myapplication.data.db.entity.emptyS


import com.google.gson.annotations.SerializedName

data class Answer(
    val image: Image,
    val isCorrect: Int,
    val order: Int,
    val text: String
)