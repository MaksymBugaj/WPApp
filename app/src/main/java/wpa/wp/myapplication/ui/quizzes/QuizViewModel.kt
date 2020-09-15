package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
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

    fun insertAnsweredQuiz(quizDetails: QuizDetails){

        Completable.fromAction {
            databaseRepository.insertAnswers(quizDetails)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            Timber.tag("NOPE").d("inserted answesr")
        }

    }

    fun downloadQuizDetails(id: Long){

        databaseRepository.getQuizDetails(id)
        compositeDisposable.add(
            databaseRepository.getQuizDetailsTemp(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("any? $it")
                if(it == null) {
                    Timber.tag("NOPE").d("loading from net")
                }
                _quizDetails.postValue(it)
            }
        )
        /*compositeDisposable.add(
        databaseRepository.getQuizDetailsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { quizDetails, throwable ->
            quizDetails?.let {
                Timber.tag("NOPE").d("we have them #QUIZVIEWMODEL ${it.id}")
               loadQuizDetails(id)
            }
            throwable?.let {
                Timber.tag("NOPE").d("Empty!!!  ${throwable.message}")
            }
        }
        )*/
    }

    private fun loadQuizDetails(id: Long){
        compositeDisposable.add(
        databaseRepository.getQuizDetailsTemp(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            Timber.tag("NOPE").d("any? $it")
            _quizDetails.postValue(it)
        }
        )
    }


    override fun onCleared() {
        super.onCleared()
        Timber.tag("NOPE").d("onCleared")
        compositeDisposable.clear()
    }
}