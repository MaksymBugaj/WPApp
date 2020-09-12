package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository

) : ViewModel() {

    val selectedQuiz = PublishSubject.create<Item>()

    private val _quizDetails = MutableLiveData<QuizDetails>()
    val quizDetails: LiveData<QuizDetails> = _quizDetails

    private val compositeDisposable = CompositeDisposable()
    init {
        Timber.tag("NOPE").d("Is it static?")
    }

    fun getQuizDetails(id: Long){
        databaseRepository.getQuizDetails(id)
        compositeDisposable.add(
        databaseRepository.getQuizDetailsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { quizDetails, throwable ->
            quizDetails?.let {
                Timber.tag("NOPE").d("we have them #QUIZVIEWMODEL")
                _quizDetails.postValue(it)
            }
            throwable?.let {
                Timber.tag("NOPE").d("Empty!!! ${throwable.message}")
            }
        }
        )


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}