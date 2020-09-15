package wpa.wp.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.data.db.QuizDao
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz

class DatabaseRepositoryImpl(
    private val quizApiRepository: QuizApiRepository,
    private val quizDao: QuizDao
) : DatabaseRepository {

    private val compositeDisposable = CompositeDisposable()
    override val itemLoaded: LiveData<Boolean>
        get() = _itemLoaded
    private val _itemLoaded = MutableLiveData(false)

    override fun loadEverything() {
        quizApiRepository.getQuizzes()
        compositeDisposable.add(
            quizApiRepository.quizDownloaded.subscribeOn(Schedulers.io()).subscribe{
                it?.let {
                    Timber.tag("NOPE").d("Lets insert")
                    insertQuiz(it)
                    for(i in it.items) getQuizDetails(i.id)
                }
            }
        )

    }

    override fun checkIfItemsLoaded(): Single<List<Item>> {
        return quizDao.checkIfItemsLoaded()
    }

    private fun insertQuiz(quiz: Quiz) {
        compositeDisposable.add(
            Completable.fromAction {
                quizDao.insertQuiz(quiz)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("quizzes in in database")
            }
        )

        compositeDisposable.add(
            Completable.fromAction {
                for (item in quiz.items) quizDao.insertItem(item)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("item in database")
                _itemLoaded.postValue(true)
            }
        )
    }

    private fun insertQuizDetails(quizDetails: QuizDetails){
        compositeDisposable.add(
            Completable.fromAction {
                quizDao.insertQuizDetails(quizDetails)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                Timber.tag("NOPE").d("quizzes details in in database")
                compositeDisposable.clear()
            }
        )
    }

    override fun getQuizzesFromApi() {
        quizApiRepository.getQuizzes()
        compositeDisposable.add(
            quizApiRepository.quizDownloaded.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
                it?.let {
                    Timber.tag("NOPE").d("Lets insert")
                        insertQuiz(it)
                }
            }
        )
    }

    override fun getQuizDetails(id: Long) {
        quizApiRepository.getQuizDetails(id)
        compositeDisposable.add(
            quizApiRepository.quizDetailsDownloaded.subscribeOn(Schedulers.io()).subscribe {
                it?.let {
                    Timber.tag("NOPE").d("Lets insert details")
                    insertQuizDetails(it)
                }
            }
        )
    }

    override fun getFinishedQuizzes(): Flowable<List<QuizDetails>> {
        return quizDao.getFinishedQuizzes()
    }

    override fun getQuizList(): Flowable<List<Item>> {
        return quizDao.getQuizItems()
    }

    override fun getFinishedQuizDetailsList(): Flowable<List<QuizDetails>> {
        return quizDao.getQuizDetailsFinished()
    }

    override fun getItemsByCategories(category: String): Single<List<Item>> {
        return quizDao.getSpecificQuizCategories(category)
    }

    override fun getQuizDetailsTemp(id: Long): Flowable<QuizDetails> {
        return quizDao.getQuizDetailsTemp(id)
    }

    override fun insertAnswers(quizDetails: QuizDetails) {
        quizDao.insertQuizDetails(quizDetails)
    }

    override fun getUnFinishedQuizzes(): Flowable<List<QuizDetails>> {
        return quizDao.getUnFinishedQuizzes()
    }
}