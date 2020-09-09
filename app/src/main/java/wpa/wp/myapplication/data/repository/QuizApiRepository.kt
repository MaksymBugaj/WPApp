package wpa.wp.myapplication.data.repository

import io.reactivex.subjects.PublishSubject
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface QuizApiRepository {

    val quizDownloaded: PublishSubject<Quiz>

    fun getQuizes()
}