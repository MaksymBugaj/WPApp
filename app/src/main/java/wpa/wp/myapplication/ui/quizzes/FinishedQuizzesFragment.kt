package wpa.wp.myapplication.ui.quizzes

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.quiz_item.view.*
import kotlinx.android.synthetic.main.quizes_list_fragment.*
import kotlinx.android.synthetic.main.quizes_list_fragment.view.*
import kotlinx.android.synthetic.main.quizes_list_fragment.view.quizzesFragment_progress
import kotlinx.android.synthetic.main.quizes_list_fragment.view.quizzesFragment_recycler
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.di.ViewModelProviderFactory
import wpa.wp.myapplication.ui.QuizAdapter
import wpa.wp.myapplication.util.split
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FinishedQuizzesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val viewModel: FinishedQuizzesViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: FinishedAdapter

    private val quizDetails = mutableListOf<QuizDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quizes_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FinishedAdapter(requireContext(), finished = true)
        view.quizzesFragment_recycler.layoutManager = LinearLayoutManager(view.context)
        view.quizzesFragment_recycler.adapter = adapter

        quizzesFragment_progress.visibility = View.GONE
        quizzesFragment_recycler.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getFinished()

        viewModel.quizDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                quizDetails.addAll(it)
                adapter.setQuizzes(it)
            }
        })


    }

}

class FinishedAdapter(
    private val context: Context,
    private val finished: Boolean
) : RecyclerView.Adapter<FinishedAdapter.FinishedViewHolder>() {

    private var quizzes: List<QuizDetails> = emptyList()


    fun setQuizzes(list: List<QuizDetails>) {
        quizzes = list
        notifyDataSetChanged()
    }

    inner class FinishedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val numberItemName: TextView = itemView.quizItem_itemName
        private val numberItemImageView: ImageView = itemView.quizItem_topImage
        private val rootLayout: CardView = itemView.quizItem_root
        private val groupFinished = itemView.quizItem_previousAttempGroup
        private val dateAttempted = itemView.quizItem_dateAttempted
        private val previousScore = itemView.quizItem_previousAttemptScore
        private val unfinished = itemView.quizItem_unfinished

        fun bind(quizDetails: QuizDetails) {
            numberItemName.text = quizDetails.title
            Picasso.with(context)
                .load(split(quizDetails.mainPhoto.url!!))
                .into(numberItemImageView)

            quizDetails.let {
                groupFinished.visibility = View.VISIBLE
                dateAttempted.text = convertLongToTime(it.finishedDate!!)
                previousScore.text = it.previousScore.toString()
                if(!finished) {
                    unfinished.visibility = View.VISIBLE
                    previousScore.text = itemView.resources.getText(R.string.awaiting_for_finish)
                }
            }
        }

        private fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_item, parent, false)
        return FinishedViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: FinishedViewHolder, position: Int) {
        holder.bind(quizzes[position])
    }
}