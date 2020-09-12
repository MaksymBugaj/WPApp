package wpa.wp.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

@Database(
    entities = [
    Quiz::class,
    QuizDetails::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WPDatabase: RoomDatabase() {

    abstract fun quizDao(): QuizDao

    abstract fun detailsDao(): DetailsDao
}