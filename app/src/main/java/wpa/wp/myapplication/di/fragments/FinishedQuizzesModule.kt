package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizzes.FinishedQuizzesFragment
import wpa.wp.myapplication.ui.quizzes.FinishedQuizzesViewModel
import wpa.wp.myapplication.ui.quizzes.UnFinishedQuizzesFragment

@Module
abstract class FinishedQuizzesModule {

    @ContributesAndroidInjector
    abstract fun contributeFinishedQuizzesFragment(): FinishedQuizzesFragment

    @ContributesAndroidInjector
    abstract fun contributeUnFinishedQuizzesFragment(): UnFinishedQuizzesFragment


    @Binds
    @IntoMap
    @ViewModelKey(FinishedQuizzesViewModel::class)
    abstract fun bindFinishedQuizzesViewModel(finishedQuizzesViewModel: FinishedQuizzesViewModel) : ViewModel
}