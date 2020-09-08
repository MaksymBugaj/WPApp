package wpa.wp.myapplication

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import wpa.wp.myapplication.di.DaggerAppComponent

class WPApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}