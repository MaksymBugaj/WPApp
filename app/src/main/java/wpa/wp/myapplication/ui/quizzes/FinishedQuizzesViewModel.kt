package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class FinishedQuizzesViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _quizDetails = MutableLiveData<List<QuizDetails>>()
    val quizDetails: LiveData<List<QuizDetails>> = _quizDetails

    private val compositeDisposable = CompositeDisposable()

    fun getFinished() {
        compositeDisposable.add(
            databaseRepository.getFinishedQuizzes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                _quizDetails.postValue(it)
            }
        )
    }

    fun getUnFinished() {
        compositeDisposable.add(
            databaseRepository.getUnFinishedQuizzes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                _quizDetails.postValue(it)
            }
        )
    }

}