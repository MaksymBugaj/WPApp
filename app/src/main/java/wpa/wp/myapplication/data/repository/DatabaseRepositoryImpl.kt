package wpa.wp.myapplication.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.QuizDao
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

class DatabaseRepositoryImpl(
    private val quizApiRepository: QuizApiRepository,
    private val quizDao: QuizDao
) : DatabaseRepository {

    private val compositeDisposable = CompositeDisposable()

    private fun insert(quiz: Quiz) {
        compositeDisposable.add(
            Completable.fromAction {
                quizDao.insertQuiz(quiz)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("quizzes in in database")
            }
        )
    }

    override fun getQuizzes() {
        quizApiRepository.getQuizes()
        compositeDisposable.add(
            quizApiRepository.quizDownloaded.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                it?.let {
                    Timber.tag("NOPE").d("Lets insert")
                        insert(it)
                }
            }
        )
    }

    override fun getQuizDetails() {
        TODO("Not yet implemented")
    }

    override fun getQuizList(): Single<Quiz> {
        return quizDao.getQuiz()
    }


}