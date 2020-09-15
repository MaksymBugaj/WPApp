package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quizes_list_fragment.*
import kotlinx.android.synthetic.main.quizes_list_fragment.view.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.ui.QuizAdapter
import javax.inject.Inject


class QuizzesListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

//    private val quizViewModel by viewModels<QuizViewModel>({activity as MainActivity }) { viewModelProviderFactory }


    private val quizzesViewModel: QuizzesListViewModel by viewModels {
        viewModelProviderFactory
    }

    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: QuizAdapter
    private val quizList = mutableListOf<Item>()
    private val quizDetails = mutableListOf<QuizDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quizes_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = QuizAdapter(requireContext(), false)
        view.quizzesFragment_recycler.layoutManager = LinearLayoutManager(view.context)
        view.quizzesFragment_recycler.adapter = adapter
        initRecyclerListener()

        quizzesFragment_progress.visibility = View.GONE
        quizzesFragment_recycler.visibility = View.VISIBLE
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgs()

        quizzesViewModel.finished.observe(viewLifecycleOwner, Observer {
            it?.let {
                quizDetails.addAll(it)
            }

        })

    }

    private fun initRecyclerListener(){
        adapter.initListener(object : QuizAdapter.OnQuizItemClickListener{
            override fun onItemClick(position: Int) {
                Timber.tag("NOPE").d("on click ${quizList[position]}")
                //quizViewModel.selectedQuiz.onNext(quizList[position])
                val action = QuizzesListFragmentDirections.actionQuizzesListFragmentToQuizFragment(quizList[position].id)
                findNavController().navigate(action)

            }
        })

    }

    private fun getArgs(){
        val args = arguments?.let { QuizzesListFragmentArgs.fromBundle(it) }
        val category = args?.category
        category?.let { quizzesViewModel.getSpecificItems(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { list, throwable ->
            list?.let {
                initRecyclerWithItems(it)
                quizList.addAll(it)
            }
            throwable?.let {

            }
        } }
    }

    private fun initRecyclerWithItems(items: List<Item>){
        adapter.setItems(items, quizDetails)
    }

}

