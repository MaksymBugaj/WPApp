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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
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
import wpa.wp.myapplication.data.db.entity.details.Answer
import wpa.wp.myapplication.data.db.entity.details.Question
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.util.split
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getArgs()

        compositeDisposable.add(
        viewModel.selectedQuiz.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Timber.tag("NOPE").d("he?")
            it?.let {
                Timber.tag("NOPE").d("passed quiz ${it.id}")
                viewModel.downloadQuizDetails(it.id)
            }
        }
        )

        viewModel.quizDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.tag("NOPE").d("quizDetails questions ${it.questions!!.size}")
                createQuiz(it)
            }
        })



    }

    private fun getArgs(){
        val args = arguments?.let { QuizFragmentArgs.fromBundle(it) }
        val id = args?.id
        id?.let { Timber.tag("NOPE").d("passed quiz getArgs ${id}")
            viewModel.downloadQuizDetails(id) }
    }

    private fun createQuiz(quizDetails: QuizDetails){
        //todo change the name?
        val quizzesSize = quizDetails.questions?.size!!

        initProgressListener(object : ProgressListener{
            override fun onChange(position: Int) {
                val progress = (position + 1).toDouble() * 100 / (quizzesSize.toDouble() + 1.0)
                Timber.tag("NOPE").d("progress $progress")
                if(progress.toInt() > 100) quizFragment_progressIndicator.progress = 99
                quizFragment_progressIndicator.progress = progress.toInt()
                quizFragment_progressIndicator.show()
            }
        })

        demoCollectionAdapter = DemoCollectionAdapter(this, quizDetails, listener)
        viewPager = quizFragment_questionsViewPager
        viewPager.adapter = demoCollectionAdapter





        val tab_layout = view?.findViewById<TabLayout>(R.id.quizFragment_questionNumber)
        TabLayoutMediator(tab_layout!!, viewPager) { tab, position ->
            tab.text = "Question ${position + 1}"


        }.attach()
    }

    private fun initProgressListener(listener: ProgressListener){
        this.listener = listener
    }

}

interface ProgressListener{
    fun onChange(position: Int)
}
interface Answers{
    fun onFinish(list: List<Pair<Int,String>>)
}

enum class ANSWER_TYPE {
    NO_ANSWER,WRONG_ANSWER,GOOD_ANSWER
}

class DemoCollectionAdapter(fragment: Fragment, private val quizDetails: QuizDetails, private val listener: ProgressListener) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = quizDetails.questions?.size!!

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        /*fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }*/
        listener.onChange(position)

        return DemoObjectFragment(quizDetails.questions?.get(position)!!)
    }

}


// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment(
    private val question: Question
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(android.R.id.text1)
            textView.text = getInt(ARG_OBJECT).toString()
        }*/
        val image = fragmentQuestion_image
        val fragmentQuestion = fragmentQuestion_question
        val answersLayout = fragmentQuestion_answersLayout



        Picasso.with(context)
            .load(split(question.image.url))
            .into(image)

        fragmentQuestion.text = question.text

        val radioGroup = RadioGroup(requireContext())
        for(answer in question.answers) {
            val radioButton = RadioButton(requireContext())
            answer.text?.let {
                radioButton.text = it
            }
            Timber.tag("NOPE").d("Answer: image ${(answer.image.url)}")
            /*answer.image?.let {
                val downloadedImage = ImageView(requireContext())
                Picasso.with(context)
                    .load(split(it.url))
                    .into(downloadedImage)

                radioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,downloadedImage,0)
            }*/

            radioGroup.addView(radioButton)
        }
        answersLayout.addView(radioGroup)

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                val radioButton = view.findViewById(checkedId) as RadioButton
                Timber.tag("NOPE").d("Answer: radioButton ${radioButton.text}")
            }
        })


    }

    private fun createAnswers(answers: List<Answer>){

    }
}