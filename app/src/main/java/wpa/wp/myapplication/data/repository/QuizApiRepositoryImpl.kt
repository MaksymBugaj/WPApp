package wpa.wp.myapplication.data.repository

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.quiz.Quiz
import wpa.wp.myapplication.data.network.ApiService

class QuizApiRepositoryImpl(
    private val apiService: ApiService
) : QuizApiRepository {

    private val compositeDisposable = CompositeDisposable()

    override val quizDownloaded: PublishSubject<Quiz>
        get() = _quizDownloaded
    private val _quizDownloaded = PublishSubject.create<Quiz>()

    override fun getQuizes() {
        apiService.getQuizes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Quiz> {
                override fun onSuccess(t: Quiz) {
                    _quizDownloaded.onNext(t)
                    Timber.tag("NOPE").d("we have data ${t}")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.tag("NOPE").d("Error during fetch API ${e.message} ${e.stackTrace} ${e.localizedMessage}")
                }

            })
    }
}