package wpa.wp.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(quiz: Quiz)

    @Query("select * from quiz_table")
    fun getQuiz(): Single<Quiz>
}