package wpa.wp.myapplication.ui.quizes

import androidx.lifecycle.ViewModel
import androidx.room.EmptyResultSetException
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.quiz.Quiz
import wpa.wp.myapplication.data.repository.DatabaseRepository
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuizzesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var repeater = false

    init {
        Timber.tag("NOPE").d("init")
        databaseRepository.getQuizzes()
    }

    fun dupa() {
        databaseRepository.getQuizList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).retry().subscribe(object: SingleObserver<Quiz>{
            override fun onSuccess(t: Quiz) {
                Timber.tag("NOPE").d("succes ${t.items.size}")
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.tag("NOPE").d("nope ${e.message}")

            }
        })

        //fixme try to repair this
       /* compositeDisposable.add(
            Single.defer{databaseRepository.getQuizList()}
                .doOnError {
                databaseRepository.getQuizzes()
                }
                .retry{attemps, error ->  error is EmptyResultSetException}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { quiz, throwable ->
                    quiz?.let { Timber.tag("NOPE").d("succes ${quiz.items.size}") }
                    throwable?.let {
                        Timber.tag("NOPE").d("nope  $throwable ${throwable.message} ")
                    }

                }
        )*/

        //databaseRepository.getQuizzes()


        Timber.tag("NOPE").d("init")

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

sealed class RoomResponse {
    object EmptyResultSetException : RoomResponse()
}