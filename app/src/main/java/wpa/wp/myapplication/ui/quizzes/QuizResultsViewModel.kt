package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class QuizResultsViewModel @Inject constructor(
private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _quizDetails = MutableLiveData<QuizDetails>()
    val quizDetails: LiveData<QuizDetails> = _quizDetails

    private val compositeDisposable = CompositeDisposable()

    fun getFinishedQuiz(id: Long){
        compositeDisposable.add(
        databaseRepository.getQuizDetailsTemp(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{ quizDetails->
            Timber.tag("NOPE").d("found answers ${quizDetails.userAnswers!!.size}")
            _quizDetails.postValue(quizDetails)
        }
        )
    }

}