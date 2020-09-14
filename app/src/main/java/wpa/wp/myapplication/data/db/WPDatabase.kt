package wpa.wp.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import wpa.wp.myapplication.data.db.converters.QuizConverters
import wpa.wp.myapplication.data.db.converters.QuizDetailsConverters
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

@Database(
    entities = [
    Quiz::class,
    QuizDetails::class,
    Item::class
    ],
    version = 4
)
@TypeConverters(QuizConverters::class, QuizDetailsConverters::class)
abstract class WPDatabase: RoomDatabase() {

    abstract fun quizDao(): QuizDao

    abstract fun detailsDao(): DetailsDao
}