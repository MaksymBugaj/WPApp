package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizzes.CategoriesListFragment
import wpa.wp.myapplication.ui.quizzes.CategoriesListViewModel
import wpa.wp.myapplication.ui.quizzes.QuizResultsFragment
import wpa.wp.myapplication.ui.quizzes.QuizResultsViewModel

@Module
abstract class QuizResultsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeQuizResultListFragment(): QuizResultsFragment


    @Binds
    @IntoMap
    @ViewModelKey(QuizResultsViewModel::class)
    abstract fun bindQuizResultsViewModel(quizResultsViewModel: QuizResultsViewModel) : ViewModel
}