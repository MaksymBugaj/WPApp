package wpa.wp.myapplication.data.repository

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

interface DatabaseRepository {

    val itemLoaded : LiveData<Boolean>

    fun loadEverything()

    fun checkIfItemsLoaded(): Single<List<Item>>

    fun getQuizzesFromApi()

    fun getFinishedQuizzes(): Flowable<List<QuizDetails>>

    fun getUnFinishedQuizzes(): Flowable<List<QuizDetails>>

    fun getQuizDetails(id: Long)

    fun getFinishedQuizDetailsList(): Flowable<List<QuizDetails>>

    fun getQuizList(): Flowable<List<Item>>

    fun getItemsByCategories(category: String): Single<List<Item>>

    fun getQuizDetailsTemp(id: Long): Flowable<QuizDetails>

    fun insertAnswers(quizDetails: QuizDetails)
}