package wpa.wp.myapplication.data.db.entity.details


import wpa.wp.myapplication.data.db.entity.details.Answer
import wpa.wp.myapplication.data.db.entity.details.ImageX

data class Question(
    val answer: String,
    val answers: List<Answer>,
    val image: ImageX,
    val order: Int,
    val text: String,
    val type: String
)