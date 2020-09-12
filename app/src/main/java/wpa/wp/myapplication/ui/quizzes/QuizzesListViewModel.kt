package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class QuizzesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

//    fun getSpecificItems(category: String): Single<List<Item>>{
//        return databaseRepository.getItemsByCategories(category)
//    }
}