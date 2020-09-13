package wpa.wp.myapplication.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface DatabaseRepository {

    //todo rename funs
    fun getQuizzes()

    fun getQuizDetails(id: Long)

    fun getFinishedQuizDetailsList(): Flowable<List<QuizDetails>>

    fun getQuizList(): Flowable<List<Item>>

    fun getItemsByCategories(category: String): Single<List<Item>>

//    fixme temporary, to be replaced
    fun getQuizDetailsTemp(id: Long): Flowable<QuizDetails>

    fun insertAnswers(quizDetails: QuizDetails)
}