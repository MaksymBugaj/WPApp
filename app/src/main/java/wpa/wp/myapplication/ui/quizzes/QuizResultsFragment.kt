package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quiz_results.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.di.ViewModelProviderFactory
import javax.inject.Inject


class QuizResultsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val quizResultsViewModel: QuizResultsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()

        quizResultsViewModel.quizDetails.observe(viewLifecycleOwner, Observer { quizDetails ->
            quizDetails.previousScore?.let {
                displayScore(quizDetails)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_results, container, false)
    }

    private fun getArgs() {
        val args = arguments?.let { QuizResultsFragmentArgs.fromBundle(it) }
        val id = args?.id
        id?.let {
            Timber.tag("NOPE").d("passed quiz getArgs ${id}")
            quizResultsViewModel.getFinishedQuiz(it)
        }
    }

    private fun displayScore(quizDetails: QuizDetails){

        var text: String = ""
        for (rate in quizDetails.rates!!){
            if(rate.to?.toInt()!! > quizDetails.previousScore!! && rate.from!!.toInt()!! <= quizDetails.previousScore) text = rate.content!!
        }
        val percentText = "${quizDetails.previousScore} %"
        quizResults_score.text = percentText
        quizResults_scoreComment.text = text


    }


}