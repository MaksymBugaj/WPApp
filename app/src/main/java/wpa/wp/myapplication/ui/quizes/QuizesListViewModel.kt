package wpa.wp.myapplication.ui.quizes

import androidx.lifecycle.ViewModel
import timber.log.Timber
import wpa.wp.myapplication.data.repository.DatabaseRepository
import javax.inject.Inject

class QuizesListViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

   fun dupa() {
       Timber.tag("NOPE").d("init")
       databaseRepository.getQuizes()
   }

}