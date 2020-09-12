package wpa.wp.myapplication.ui.quizzes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.di.ViewModelProviderFactory
import javax.inject.Inject

class QuizFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModel: QuizViewModel by viewModels {
        viewModelFactory
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgs()

        compositeDisposable.add(
        viewModel.selectedQuiz.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Timber.tag("NOPE").d("he?")
            it?.let {
                Timber.tag("NOPE").d("passed quiz ${it.id}")
                viewModel.getQuizDetails(it.id)
            }
        }
        )

        viewModel.quizDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.tag("NOPE").d("quizDetails ${it.questions!!.size}")
            }
        })



    }

    private fun getArgs(){
        val args = arguments?.let { QuizFragmentArgs.fromBundle(it) }
        val id = args?.id
        id?.let { viewModel.getQuizDetails(id) }
    }

    private fun createQuiz(quizDetails: QuizDetails){

    }

}