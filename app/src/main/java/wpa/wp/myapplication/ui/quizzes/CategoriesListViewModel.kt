package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.ViewModel
import io.reactivex.FlowableOnSubscribe
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val data = PublishSubject.create<List<Item>>()


    init {
        Timber.tag("NOPE").d("init")
        databaseRepository.getQuizzes()

    }

    fun getCategories() {
        compositeDisposable.add(
        databaseRepository.getQuizList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if(it == null || it.isEmpty()) databaseRepository.getQuizzes()
                data.onNext(it)
        }
        )

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

                //.subscribe(object: FlowableOnSubscribe<List<Item>>{
                //            override fun onSuccess(t: List<Item>) {
                //                Timber.tag("NOPE").d("succes ${t.size}")
                //                data.onNext(t)
                //            }
                //
                //            override fun onSubscribe(d: Disposable) {
                //                compositeDisposable.add(d)
                //            }
                //
                //            override fun onError(e: Throwable) {
                //                Timber.tag("NOPE").d("nope ${e.message}")
                //
                //            }
                //        })
        )*/

        //databaseRepository.getQuizzes()


//        Timber.tag("NOPE").d("init")

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

sealed class RoomResponse {
    object EmptyResultSetException : RoomResponse()
}