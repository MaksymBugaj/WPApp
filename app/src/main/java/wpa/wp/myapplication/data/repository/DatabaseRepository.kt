package wpa.wp.myapplication.data.repository

import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface DatabaseRepository {

    //todo rename funs
    fun getQuizzes()

    fun getQuizDetails(id: Long)

    fun getQuizDetailsList(): Single<QuizDetails>

    fun getQuizList(): Single<Quiz>

//    fun getItemsByCategories(category: String): Single<List<Item>>
}