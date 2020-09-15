package wpa.wp.myapplication.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.quiz_fragment.*
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.Question
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.util.split
import java.util.*
import javax.inject.Inject

class QuizFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModel: QuizViewModel by viewModels {
        viewModelFactory
    }

    private val compositeDisposable = CompositeDisposable()
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var listener: ProgressListener
    private val listOfAnswers = mutableMapOf<String, String>()
    // rotfl ;)
    private var quizNotDisplayed = true
    private var quizSaved = false

    //holding quiz to save in db in case of leaving test
    private lateinit var quizDetails: QuizDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgs()

        compositeDisposable.add(
            viewModel.selectedQuiz.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    Timber.tag("NOPE").d("he?")
                    it?.let {
                        Timber.tag("NOPE").d("Quiz passed quiz ${it.id}")
                        viewModel.downloadQuizDetails(it.id)
                    }
                }
        )

        viewModel.quizDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.tag("NOPE").d("quizDetails questions ${it.questions!!.size}")
                if(quizNotDisplayed) {
                    createQuiz(it)
                    quizNotDisplayed = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("NOPE").d("on Reesume")
    }

    private fun getArgs() {
        val args = arguments?.let { QuizFragmentArgs.fromBundle(it) }
        val id = args?.id
        id?.let {
            Timber.tag("NOPE").d("passed quiz getArgs ${id}")
            viewModel.downloadQuizDetails(id)
        }
    }

    private fun createQuiz(quizDetails: QuizDetails) {
        //todo change the name?
        this.quizDetails = quizDetails
        val quizzesSize = quizDetails.questions.size

        initProgressListener(object : ProgressListener {
            override fun onAnswerSelected(answer: Pair<String, String>) {
                listOfAnswers[answer.first] = answer.second
                Timber.tag("NOPE")
                    .d("Answer received ${answer.first} s: ${answer.second} ${listOfAnswers.size}")
            }

            override fun buttonEndListener() {
                checkAnswers(quizDetails)
            }
        })
        val previousAnswers = quizDetails.userAnswers
         if(quizDetails.unfinished == true) {
             Timber.tag("NOPE")
                 .d("Unfinished!! ${previousAnswers?.size} ")
             demoCollectionAdapter = DemoCollectionAdapter(this, quizDetails, listener, previousAnswers)
         } else demoCollectionAdapter = DemoCollectionAdapter(this, quizDetails, listener, null)


        viewPager = quizFragment_questionsViewPager
        viewPager.adapter = demoCollectionAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                val progress = (position + 1).toDouble() * 100 / (quizzesSize.toDouble())
                Timber.tag("NOPE").d("progress $progress")
                if (progress.toInt() > 100) quizFragment_progressIndicator.progress = 99
                quizFragment_progressIndicator.progress = progress.toInt()
                quizFragment_progressIndicator.show()
            }
        })

        val tabLayout = view?.findViewById<TabLayout>(R.id.quizFragment_questionNumber)
        TabLayoutMediator(tabLayout!!, viewPager) { tab, position ->
            tab.text = "Question ${position + 1}"
        }.attach()
    }

    private fun initProgressListener(listener: ProgressListener) {
        this.listener = listener
    }

    private fun checkAnswers(quizDetails: QuizDetails) {
        var countedCorrectAnswers = 0
        val correctAnswer = mutableMapOf<String, Boolean>()
        for (i in quizDetails.questions.indices) {
            if(listOfAnswers.contains(quizDetails.questions[i].text!!)) {
                val getAnswer = listOfAnswers.getValue(quizDetails.questions[i].text!!)
                for (j in quizDetails.questions[i].answers?.indices!!) {
                    if (getAnswer == quizDetails.questions[i].answers?.get(j)?.text!!) {
                        quizDetails.questions[i].answers?.get(j)?.isCorrect?.let {
                            if (quizDetails.questions[i].answers?.get(j)?.isCorrect!! == 1) {
                                correctAnswer[quizDetails.questions[i].text!!] = true
                                countedCorrectAnswers += 1
                            }
                        }
                    }
                }
            }
        }
        Timber.tag("NOPE").d("listOfAnswers!!! ${listOfAnswers.size} counted correct: $countedCorrectAnswers")

        var percent = 0
        if(countedCorrectAnswers == 0){} else percent = (countedCorrectAnswers * 100/ quizDetails.questions.size)
        Timber.tag("NOPE").d("Finish!!! percent ${percent}")
        val map: Map<String, String> = listOfAnswers
        val quizToInsert = quizDetails.copy(
            userAnswers = map,
            finishedDate = getFinishedDate(),
            unfinished = false,
            previousScore = percent.toInt()
        )
        quizSaved = true
        viewModel.insertAnsweredQuiz(quizToInsert)
        saveQuizAndNavigateToResults(quizToInsert)
    }


    private fun saveQuizAndNavigateToResults(quizDetails: QuizDetails) {
        Timber.tag("NOPE").d("Finish!!! ${listOfAnswers.size}")
        val action = QuizFragmentDirections.actionQuizFragmentToQuizResultsFragment(quizDetails.id)
        findNavController().navigate(action)

    }

    private fun getFinishedDate(): Long {
        val date = Date()
        return date.time
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!quizSaved) {
            if (::quizDetails.isInitialized) {
                val quizToInsert = quizDetails.copy(
                    userAnswers = listOfAnswers,
                    finishedDate = getFinishedDate(),
                    unfinished = true
                )
                Timber.tag("NOPE")
                    .d("onStop!! ")
                viewModel.insertAnsweredQuiz(quizToInsert)
            }
        }
    }
}

interface ProgressListener {
    fun onAnswerSelected(answer: Pair<String, String>)
    fun buttonEndListener()
}

class DemoCollectionAdapter(
    fragment: Fragment,
    private val quizDetails: QuizDetails,
    private val listener: ProgressListener,
    private val map: Map<String, String>?
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = quizDetails.questions.size

    override fun createFragment(position: Int): Fragment {

        return if (position == itemCount - 1) DemoObjectFragment(
            quizDetails.questions[position],
            listener,
            true,
            map
        )
        else
            DemoObjectFragment(quizDetails.questions[position], listener, false, map)
    }
}

class DemoObjectFragment(
    private val question: Question,
    private val listener: ProgressListener,
    private val showButton: Boolean = false,
    private val map: Map<String, String>?
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image = fragmentQuestion_image
        val fragmentQuestion = fragmentQuestion_question
        val answersLayout = fragmentQuestion_answersLayout
        val finishButton = fragmentQuestion_buttonFinish

        if(question.image?.url!!.isNotEmpty()){
            Picasso.with(context)
                .load(split(question.image?.url!!))
                .into(image)
        }

        fragmentQuestion.text = question.text

        val radioGroup = RadioGroup(requireContext())
        for (answer in question.answers!!) {
            val radioButton = RadioButton(requireContext())
            answer.text?.let {
                radioButton.text = it

            }
            radioGroup.addView(radioButton)
            if(!map.isNullOrEmpty()){
                if(map.containsKey(question.text)){
                    if(map.containsValue(radioButton.text.toString())) {
                        radioButton.isChecked = true
                    }
                }
            }
        }
        answersLayout.addView(radioGroup)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById(checkedId) as RadioButton
            Timber.tag("NOPE").d("Answer: radioButton ${radioButton.text}")
            listener.onAnswerSelected(Pair(question.text!!, radioButton.text.toString()))
        }
        if (showButton) {
            finishButton.visibility = View.VISIBLE
            finishButton.setOnClickListener {
                listener.buttonEndListener()
            }
        }


    }
}