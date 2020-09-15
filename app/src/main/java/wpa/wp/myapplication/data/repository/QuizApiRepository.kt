package wpa.wp.myapplication.data.repository

import io.reactivex.subjects.PublishSubject
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface QuizApiRepository {

    val quizDownloaded: PublishSubject<Quiz>
    val quizDetailsDownloaded: PublishSubject<QuizDetails>

    fun getQuizzes()

    fun getQuizDetails(id: Long)
}