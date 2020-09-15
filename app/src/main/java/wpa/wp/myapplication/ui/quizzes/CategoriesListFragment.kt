package wpa.wp.myapplication.ui.quizzes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import wpa.wp.myapplication.data.db.entity.quiz.CategoryX
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.di.ViewModelProviderFactory
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

        compositeDisposable.add(
            categoriesListViewModel.data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.let { showQuizzesList(it) }
                }
        )

        categoriesListViewModel.dataDownloaded.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    Timber.tag("NOPE").d("click available")
                    quizzesFragment_progress.visibility = View.GONE
                    quizzesFragment_recycler.visibility = View.VISIBLE
                }
            }

        })

        //fixme out of memory error
        if(!restorePrefData()){
//            loadAll()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoriesListViewModel.getCategories()
    }

    private fun showQuizzesList(quiz: List<Item>) {
        for (item in quiz) {
            if (!categories.contains(item.category)) categories.add(item.category)
        }
        adapter.setCategories(categories)
    }

    private fun initRecyclerListener() {
        adapter.initListener(object : QuizAdapter.OnQuizItemClickListener {
            override fun onItemClick(position: Int) {
                val action =
                    CategoriesListFragmentDirections.actionCategoriesListFragmentToQuizzesListFragment(
                        categories[position].name
                    )
                findNavController().navigate(action)
            }
        })
    }

    private fun loadAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.download)
        builder.setMessage(resources.getString(R.string.download_data))
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(resources.getText(R.string.download)) { dialog, _ ->
            categoriesListViewModel.downloadAll()
            savePrefsData()
            dialog.dismiss()
        }
        builder.show()
    }

    private fun restorePrefData(): Boolean {
        val pref = requireContext().getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        )
        return pref.getBoolean("bulkDataLoaded", false)
    }

    private fun savePrefsData() {
        val pref = requireContext().getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putBoolean("bulkDataLoaded", true)
        editor.commit()
    }

}