package wpa.wp.myapplication.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import wpa.wp.myapplication.ui.MainActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity
}