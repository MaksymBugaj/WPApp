package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quizes_list_fragment.view.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.quiz.CategoryX
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.data.db.entity.quiz.Quiz
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.ui.MainActivity
import wpa.wp.myapplication.ui.QuizAdapter
import javax.inject.Inject

class CategoriesListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val categoriesListViewModel: CategoriesListViewModel by viewModels {
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
        adapter = QuizAdapter(requireContext(), true)
        view.quizzesFragment_recycler.layoutManager = LinearLayoutManager(view.context)
        view.quizzesFragment_recycler.adapter = adapter
        initRecyclerListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //categoriesListViewModel.dupa()

        compositeDisposable.add(
            categoriesListViewModel.data.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.let { showQuizzesList(it) }
                }
        )
    }

    private fun showQuizzesList(quiz: Quiz){

        val items = mutableListOf<Item>()
        for(item in quiz.items){
//            Timber.tag("NOPE").d("categoryXXX ${item.mainPhoto.url}")
//            Timber.tag("NOPE").d("categoryXXX ${item.id}")

            if (!categories.contains(item.category))categories.add(item.category)
            if (!quizList.contains(item))quizList.add(item)

        }
//        for(item in categories) Timber.tag("NOPE").d("category ${item.name}")
        adapter.setCategories(categories)

    }

    private fun initRecyclerListener(){
        adapter.initListener(object : QuizAdapter.OnQuizItemClickListener{
            override fun onItemClick(position: Int) {
                Timber.tag("NOPE").d("on click ${quizList[position]}")
                //quizViewModel.selectedQuiz.onNext(quizList[position])
//                val action = QuizzesListFragmentDirections.actionQuizesListFragmentToQuizFragment(quizList[position].id)
//
                val action = CategoriesListFragmentDirections.actionCategoriesListFragmentToQuizzesListFragment(categories[position].name)
                findNavController().navigate(action)
            }
        })

    }

}