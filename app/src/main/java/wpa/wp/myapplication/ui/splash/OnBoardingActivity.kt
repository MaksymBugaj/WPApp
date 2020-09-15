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
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import wpa.wp.myapplication.R
import wpa.wp.myapplication.ui.MainActivity
import wpa.wp.myapplication.util.language.LanguagePreference


class OnBoardingActivity : DaggerAppCompatActivity() {

    private var screenPager: ViewPager? = null
    var introViewPagerAdapter: IntroViewPager? = null
    var tabIndicator: TabLayout? = null
    var btnNext: Button? = null
    var position = 0
    var btnGetStarted: Button? = null
    var btnAnim: Animation? = null
    var tvSkip: TextView? = null
    private lateinit var langPreference: LanguagePreference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
        setContentView(R.layout.activity_on_boarding)

        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        tvSkip = findViewById(R.id.tv_skip)


        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                resources.getText(R.string.category_selection).toString(),
                resources.getText(R.string.category_selection_selection).toString(),
                R.drawable.ic_quiz_list
            )
        )
        mList.add(
            ScreenItem(
                resources.getText(R.string.finished_screen).toString(),
                resources.getText(R.string.finished_screen_selection).toString(),
                R.drawable.ic_done_all
            )
        )
        mList.add(
            ScreenItem(
                resources.getText(R.string.multi_language).toString(),
                resources.getText(R.string.multi_language_selection).toString(),
                R.drawable.ic_poland_flag
            )
        )

        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPager(this, mList)
        screenPager!!.setAdapter(introViewPagerAdapter)

        tabIndicator!!.setupWithViewPager(screenPager)

        btnNext!!.setOnClickListener {
                position = screenPager!!.getCurrentItem()
                if (position < mList.size) {
                    position++
                    screenPager!!.setCurrentItem(position)
                }
                if (position == mList.size - 1) {
                    loadLastScreen()
                }
        }

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
                    loadLastScreen()
                }
            }
        })

        btnGetStarted!!.setOnClickListener {
                val mainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainActivity)
                savePrefsData()
                finish()
        }
        tvSkip!!.setOnClickListener{
                screenPager!!.setCurrentItem(mList.size)
        }

        if(!restorePrefData()) chooseLanguage()
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
        editor.apply()
    }


    private fun loadLastScreen() {
        btnNext!!.visibility = View.INVISIBLE
        btnGetStarted!!.visibility = View.VISIBLE
        tvSkip!!.visibility = View.INVISIBLE
        tabIndicator!!.visibility = View.INVISIBLE
        btnGetStarted!!.animation = btnAnim
    }

    private fun changeVisibility(){
        btnNext!!.visibility = View.VISIBLE
        btnGetStarted!!.visibility = View.GONE
        tvSkip!!.visibility = View.VISIBLE
        tabIndicator!!.visibility = View.VISIBLE
    }

    private fun chooseLanguage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.choose_language)
        builder.setMessage(resources.getString(R.string.choose_language_text))
        builder.setNeutralButton("english") { dialog, _ ->
            selectLanguage("en")
            dialog.dismiss()
        }
        builder.setPositiveButton("polish") { dialog, _ ->
            selectLanguage("pl")
            dialog.dismiss()
        }
        builder.show()
    }

    private fun selectLanguage(lang: String) {
        val languagePreference = LanguagePreference(applicationContext)
        languagePreference.setLanguage(lang)
    }


}

data class ScreenItem(
    val title: String,
    val description: String,
    val screenImg: Int
)