package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizes.QuizzesListFragment
import wpa.wp.myapplication.ui.quizes.QuizzesListViewModel

@Module
abstract class QuizesListFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeQuizesListFragment(): QuizzesListFragment


    @Binds
    @IntoMap
    @ViewModelKey(QuizzesListViewModel::class)
    abstract fun bindQuizesListViewModel(quizzesListViewModel: QuizzesListViewModel) : ViewModel
}