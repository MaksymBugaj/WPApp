package wpa.wp.myapplication

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import wpa.wp.myapplication.di.DaggerAppComponent

class WPApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        Stetho.initializeWithDefaults(this)
        return DaggerAppComponent.builder().application(this).build()
    }
}