package wpa.wp.myapplication.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import wpa.wp.myapplication.ui.MainActivity
import wpa.wp.myapplication.ui.splash.OnBoardingActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeOnBoardingActivity(): OnBoardingActivity
}