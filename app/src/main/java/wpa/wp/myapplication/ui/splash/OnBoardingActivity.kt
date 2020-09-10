package wpa.wp.myapplication.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import wpa.wp.myapplication.R
import wpa.wp.myapplication.ui.MainActivity


class OnBoardingActivity : DaggerAppCompatActivity() {

    private var screenPager: ViewPager? = null
    var introViewPagerAdapter: IntroViewPager? = null
    var tabIndicator: TabLayout? = null
    var btnNext: Button? = null
    var position = 0
    var btnGetStarted: Button? = null
    var btnAnim: Animation? = null
    var tvSkip: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // make the activity on full screen


        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        // when this activity is about to be launch we need to check if its openened before or not


        // when this activity is about to be launch we need to check if its openened before or not
        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
        setContentView(R.layout.activity_on_boarding)


        supportActionBar!!.hide()

        // ini views

        // ini views
        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        tvSkip = findViewById(R.id.tv_skip)

        // fill list screen


        // fill list screen
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Fresh Food",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img1
            )
        )
        mList.add(
            ScreenItem(
                "Fast Delivery",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img2
            )
        )
        mList.add(
            ScreenItem(
                "Easy Payment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img3
            )
        )

        // setup viewpager

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPager(this, mList)
        screenPager!!.setAdapter(introViewPagerAdapter)


        // setup tablayout with viewpager


        // setup tablayout with viewpager
        tabIndicator!!.setupWithViewPager(screenPager)

        // next button click Listner


        // next button click Listner
        btnNext!!.setOnClickListener {
                position = screenPager!!.getCurrentItem()
                if (position < mList.size) {
                    position++
                    screenPager!!.setCurrentItem(position)
                }
                if (position == mList.size - 1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button
                    loaddLastScreen()
                }

        }

        // tablayout add change listener

        tabIndicator!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab!!.position == mList.size - 1) {
                    changeVisibility()
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }

        })

        // tablayout add change listener


        // Get Started button click listener


        // Get Started button click listener
        btnGetStarted!!.setOnClickListener {

                //open main activity
                val mainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainActivity)
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData()
                finish()

        }

        // skip button click listener


        // skip button click listener
        tvSkip!!.setOnClickListener{

                screenPager!!.setCurrentItem(mList.size)

        }
    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        )
        return pref.getBoolean("isIntroOpnend", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private fun loaddLastScreen() {
        btnNext!!.visibility = View.INVISIBLE
        btnGetStarted!!.visibility = View.VISIBLE
        tvSkip!!.visibility = View.INVISIBLE
        tabIndicator!!.visibility = View.INVISIBLE
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted!!.animation = btnAnim
    }

    private fun changeVisibility(){
        btnNext!!.visibility = View.VISIBLE
        btnGetStarted!!.visibility = View.GONE
        tvSkip!!.visibility = View.VISIBLE
        tabIndicator!!.visibility = View.VISIBLE
    }
}

data class ScreenItem(
    val title: String,
    val description: String,
    val screenImg: Int
)