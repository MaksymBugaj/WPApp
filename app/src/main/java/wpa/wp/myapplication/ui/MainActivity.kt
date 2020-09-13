package wpa.wp.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import wpa.wp.myapplication.R

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_holder)

        setupToolbar()

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
}