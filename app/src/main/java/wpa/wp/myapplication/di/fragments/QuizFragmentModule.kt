package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizzes.QuizFragment
import wpa.wp.myapplication.ui.quizzes.QuizViewModel
import javax.inject.Singleton

@Module
abstract class QuizFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeQuizFragment(): QuizFragment


    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun bindQuizViewModel(quizViewModel: QuizViewModel) : ViewModel
}