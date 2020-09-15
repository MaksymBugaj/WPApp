package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val data = PublishSubject.create<List<Item>>()
    private val _dataDownloaded = MutableLiveData<Boolean>(databaseRepository.itemLoaded.value)
    val dataDownloaded = _dataDownloaded


    init {
        Timber.tag("NOPE").d("init")
        databaseRepository.checkIfItemsLoaded()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Item>> {
                override fun onSuccess(t: List<Item>) {
                    data.onNext(t)
                    if(t.isNotEmpty()) _dataDownloaded.postValue(true)
                    if (t.isEmpty()) loadFromApi()
                    Timber.tag("NOPE").d("success ${t.size}")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.tag("NOPE").d("error")
                    loadFromApi()
                }
            })

    }

    private fun loadFromApi() {
        databaseRepository.getQuizzesFromApi()
        getCategories()
    }

    fun getCategories() {
        compositeDisposable.add(
            databaseRepository.getQuizList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                data.onNext(it)
                    _dataDownloaded.postValue(true)
            }
        )
    }

    fun downloadAll(){
        databaseRepository.loadEverything()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
