package wpa.wp.myapplication.di.fragments

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import wpa.wp.myapplication.di.ViewModelKey
import wpa.wp.myapplication.ui.quizzes.CategoriesListFragment
import wpa.wp.myapplication.ui.quizzes.CategoriesListViewModel
import wpa.wp.myapplication.ui.quizzes.QuizzesListViewModel

@Module
abstract class CategoriesListFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCategoriesListFragment(): CategoriesListFragment


    @Binds
    @IntoMap
    @ViewModelKey(CategoriesListViewModel::class)
    abstract fun bindCategoriesListViewModel(categoriesListViewModel: CategoriesListViewModel) : ViewModel
}