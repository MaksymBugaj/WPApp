package wpa.wp.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(quiz: Quiz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuizDetails(quizDetails: QuizDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    //fixme improve queries?
    @Query("select * from item_table")
    fun getQuizItems(): Flowable<List<Item>>

    @Query("select * from quiz_details")
    fun getQuizDetails(): Single<QuizDetails>

    @Query("select * from item_table where categoryX_name =:categoryName ")
    fun getSpecificQuizCategories(categoryName: String): Single<List<Item>>

    //fixme temporary dupa fun
    @Query("select * from quiz_details where id=:id")
    fun getQuizDetailsTemp(id: Long): Flowable<QuizDetails>
}