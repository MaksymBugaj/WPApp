package wpa.wp.myapplication.data.db.entity.emptyS


import com.google.gson.annotations.SerializedName

data class Question(
    val answer: String,
    val answers: List<Answer>,
    val image: ImageX,
    val order: Int,
    val text: String,
    val type: String
)