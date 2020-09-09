package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizes.QuizesListFragment
import wpa.wp.myapplication.ui.quizes.QuizesListViewModel

@Module
abstract class QuizesListFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeQuizesListFragment(): QuizesListFragment


    @Binds
    @IntoMap
    @ViewModelKey(QuizesListViewModel::class)
    abstract fun bindQuizesListViewModel(quizesListViewModel: QuizesListViewModel) : ViewModel
}