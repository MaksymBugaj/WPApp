package wpa.wp.myapplication.data.repository

import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.QuizDao
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

class DatabaseRepositoryImpl(
    private val quizApiRepository: QuizApiRepository,
    private val quizDao: QuizDao
) : DatabaseRepository {

    private val compositeDisposable = CompositeDisposable()

    private fun insert(list: Quiz) {
        compositeDisposable.add(
            Completable.fromAction {
                quizDao.insertQuiz(list)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("cummed in database")
            }
        )
    }

    override fun getQuizes() {
        quizApiRepository.getQuizes()
        compositeDisposable.add(
            quizApiRepository.quizDownloaded.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                it?.let {

                        insert(it)

                }
            }
        )
    }

    override fun getQuizDetails() {
        TODO("Not yet implemented")
    }
}