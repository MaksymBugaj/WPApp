package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizzes.QuizzesListFragment
import wpa.wp.myapplication.ui.quizzes.QuizzesListViewModel

@Module
abstract class QuizzesListFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeQuizzesListFragment(): QuizzesListFragment


    @Binds
    @IntoMap
    @ViewModelKey(QuizzesListViewModel::class)
    abstract fun bindQuizzesListViewModel(quizzesListViewModel: QuizzesListViewModel) : ViewModel
}