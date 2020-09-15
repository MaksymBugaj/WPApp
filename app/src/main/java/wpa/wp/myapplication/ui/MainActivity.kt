package wpa.wp.myapplication.ui

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.util.language.ContextWrapper
import wpa.wp.myapplication.util.language.LanguagePreference
import java.util.concurrent.TimeUnit
import wpa.wp.myapplication.util.showDialog

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var navController: NavController

    private val compositeDisposable = CompositeDisposable()
    private lateinit var langPreference: LanguagePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_holder)

        setupToolbar()

        compositeDisposable.add(
            Observable.interval(0, 10, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap { isInternetOn(this) }
                .subscribe {
                    if (!it) {
                        Timber.tag("NOPE").d("We have no interneeeeet!!!!1")
                        showDialog(this)
                    }
                }
        )

        bottomNav.setupWithNavController(navController)

    }

    private fun setupToolbar() {
        val toolbarLayout: Toolbar = topAppBar
        setSupportActionBar(toolbarLayout)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.categoriesListFragment,
                R.id.quizFragment,
                R.id.quizzesListFragment,
                R.id.quizResultsFragment

            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun isInternetOn(context: Context): Observable<Boolean>? {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    override fun attachBaseContext(newBase: Context?) {
        langPreference = LanguagePreference(newBase!!)
        val lang = langPreference.getLanguage()
        super.attachBaseContext(ContextWrapper.wrap(newBase,lang))
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}