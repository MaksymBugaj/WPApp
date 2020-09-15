package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.quizes_list_fragment.*
import kotlinx.android.synthetic.main.quizes_list_fragment.view.*
import kotlinx.android.synthetic.main.quizes_list_fragment.view.quizzesFragment_progress
import kotlinx.android.synthetic.main.quizes_list_fragment.view.quizzesFragment_recycler
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.di.ViewModelProviderFactory
import javax.inject.Inject

class UnFinishedQuizzesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModel: FinishedQuizzesViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: FinishedAdapter

    private val quizDetails = mutableListOf<QuizDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quizes_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FinishedAdapter(requireContext(), finished = false)
        view.quizzesFragment_recycler.layoutManager = LinearLayoutManager(view.context)
        view.quizzesFragment_recycler.adapter = adapter

        quizzesFragment_progress.visibility = View.GONE
        quizzesFragment_recycler.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getUnFinished()

        viewModel.quizDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                quizDetails.addAll(it)
                adapter.setQuizzes(it)
            }
        })


    }

}