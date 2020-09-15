package wpa.wp.myapplication.data.repository

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Quiz
import wpa.wp.myapplication.data.network.ApiService
import wpa.wp.myapplication.data.network.NoConnectivityException

class QuizApiRepositoryImpl(
    private val apiService: ApiService
) : QuizApiRepository {

    private val compositeDisposable = CompositeDisposable()

    override val quizDownloaded: PublishSubject<Quiz>
        get() = _quizDownloaded
    private val _quizDownloaded = PublishSubject.create<Quiz>()

    override val quizDetailsDownloaded: PublishSubject<QuizDetails>
        get() = _quizDetailsDownloaded
    private val _quizDetailsDownloaded = PublishSubject.create<QuizDetails>()

    override fun getQuizzes() {
        try{
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
                    Timber.tag("NOPE")
                        .d("Error during fetch API ${e.message} ${e.stackTrace} ${e.localizedMessage}")
                }

            })
        } catch (e: NoConnectivityException) {
            Timber.tag("NOPE")
                .d("No internet connection. ${e.message}")
        }
    }

    override fun getQuizDetails(id: Long) {
        try {
            apiService.getSpecificQuiz(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<QuizDetails> {
                    override fun onSuccess(t: QuizDetails) {
                        _quizDetailsDownloaded.onNext(t)
                        Timber.tag("NOPE").d("we have data Quiz ${t}")
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Timber.tag("NOPE")
                            .d("Error during fetch QUIZ API ${e.message}")
                    }

                })
        } catch (e: NoConnectivityException) {
            Timber.tag("NOPE")
                .d("No internet connection. ${e.message}")
        }
    }
}