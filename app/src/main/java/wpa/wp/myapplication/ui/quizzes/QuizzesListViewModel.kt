package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class QuizzesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _finished = MutableLiveData<List<QuizDetails>>()
    val finished: LiveData<List<QuizDetails>> = _finished

    private val compositeDisposable = CompositeDisposable()

    init {
        getFinishedQuizzes()
    }

    fun getSpecificItems(category: String): Single<List<Item>>{
        return databaseRepository.getItemsByCategories(category)
    }

    private fun getFinishedQuizzes() {
        Timber.tag("NOPE").d("get Finished")
        compositeDisposable.add(
        databaseRepository.getFinishedQuizDetailsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            _finished.postValue(it)
        }
        )
    }
}