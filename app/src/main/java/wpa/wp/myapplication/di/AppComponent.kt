package wpa.wp.myapplication.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import wpa.wp.myapplication.WPApp
import wpa.wp.myapplication.di.fragments.QuizFragmentModule
import wpa.wp.myapplication.di.fragments.CategoriesListFragmentModule
import wpa.wp.myapplication.di.fragments.QuizResultsFragmentModule
import wpa.wp.myapplication.di.fragments.QuizzesListFragmentModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        CategoriesListFragmentModule::class,
        QuizFragmentModule::class,
        QuizzesListFragmentModule::class,
        QuizResultsFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<WPApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}