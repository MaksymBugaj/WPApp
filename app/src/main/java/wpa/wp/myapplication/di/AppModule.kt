package wpa.wp.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import wpa.wp.myapplication.data.db.QuizDao
import wpa.wp.myapplication.data.db.WPDatabase
import wpa.wp.myapplication.data.network.ApiService
import wpa.wp.myapplication.data.repository.DatabaseRepository
import wpa.wp.myapplication.data.repository.DatabaseRepositoryImpl
import wpa.wp.myapplication.data.repository.QuizApiRepository
import wpa.wp.myapplication.data.repository.QuizApiRepositoryImpl
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): WPDatabase {
        return Room.databaseBuilder(
            app as Context, WPDatabase::class.java,"WPDB.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCourseDao(db: WPDatabase): QuizDao {
        return db.quizDao()
    }

    @Singleton
    @Provides
    fun provideQuizApiRepository(apiService: ApiService): QuizApiRepository {
        return QuizApiRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(
        quizApiRepository: QuizApiRepository,
        quizDao: QuizDao
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(quizApiRepository, quizDao)
    }
}