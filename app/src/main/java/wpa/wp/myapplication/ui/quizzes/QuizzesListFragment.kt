package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quizes_list_fragment.view.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.quiz.CategoryX
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.ui.MainActivity
import wpa.wp.myapplication.ui.QuizAdapter
import javax.inject.Inject

class QuizzesListFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val quizViewModel by viewModels<QuizViewModel>({activity as MainActivity }) { viewModelProviderFactory }


    private val quizzesViewModel: QuizzesListViewModel by viewModels {
        viewModelProviderFactory
    }

    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: QuizAdapter
    private val quizList = mutableListOf<Item>()
    private val categories = mutableListOf<CategoryX>()

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
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgs()


    }

    private fun initRecyclerListener(){
        adapter.initListener(object : QuizAdapter.OnQuizItemClickListener{
            override fun onItemClick(position: Int) {
                Timber.tag("NOPE").d("on click ${quizList[position]}")
                //quizViewModel.selectedQuiz.onNext(quizList[position])
//                val action = QuizzesListFragmentDirections.actionQuizesListFragmentToQuizFragment(quizList[position].id)
//                findNavController().navigate(action)

            }
        })

    }

    private fun getArgs(){
        val args = arguments?.let { QuizzesListFragmentArgs.fromBundle(it) }
        val category = args?.category
//        category?.let { quizzesViewModel.getSpecificItems(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { list, throwable ->
//            list?.let {
//                initRecyclerWithItems(it)
//            }
//            throwable?.let {
//
//            }
//        } }
    }

    private fun initRecyclerWithItems(items: List<Item>){
        adapter.setItems(items)
    }

}