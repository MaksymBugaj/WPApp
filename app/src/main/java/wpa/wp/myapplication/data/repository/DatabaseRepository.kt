package wpa.wp.myapplication.data.repository

import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface DatabaseRepository {

    fun getQuizzes()

    fun getQuizDetails()

    fun getQuizList(): Single<Quiz>
}