package wpa.wp.myapplication.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import wpa.wp.myapplication.WPApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<WPApp>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}