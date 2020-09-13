package wpa.wp.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.quiz_item.view.*
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.CategoryX
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.util.split
import java.text.SimpleDateFormat
import java.util.*


class QuizAdapter(
    private val context: Context,
    private val isCategory: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Item> = emptyList()
    private var itemsFinished: List<QuizDetails> = emptyList()
    private var categories: List<CategoryX> = emptyList()
    private lateinit var listener: OnQuizItemClickListener

    private val isCategoryTrue = 1
    private val isCategoryFalse = 0


    /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val inflater = LayoutInflater.from(parent.context)
         val viewHolderBinding = QuizItemBinding.inflate(inflater, parent, false)
         return ViewHolder(
             binding = viewHolderBinding,
             lifecycleOwner = lifecycleOwner,
             listenerItem = onQuizClickListener
         )
     }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(layout, listenerItem = listener)*/
        return when (isCategory) {
            true -> {
                val layout =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_item, parent, false)
                CategoryViewHolder(layout, listener)
            }
            else -> {
                val layout =
                    LayoutInflater.from(parent.context).inflate(R.layout.quiz_item, parent, false)
                QuizViewHolder(layout, listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return when (isCategory) {
            true -> categories.size
            else -> items.size
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (isCategory){
//            true -> isCategoryTrue
//            else -> isCategoryFalse
//        }
//    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (isCategory) {
            true -> {
                (holder as CategoryViewHolder).bind(categories[position])
            }
            false -> {
                if (itemsFinished.size != 0) {
                    (holder as QuizViewHolder).bind(
                        items[position],
                        getProperQuizDetails(items[position].id, itemsFinished)
                    )
                } else (holder as QuizViewHolder).bind(items[position], null)
            }

        }
    }

    fun getProperQuizDetails(id: Long, list: List<QuizDetails>): QuizDetails? {
        return list.firstOrNull() { it.id == id }
    }

    fun initListener(listener: OnQuizItemClickListener) {
        this.listener = listener
    }

    fun setItems(itemsList: List<Item>, finishedList: List<QuizDetails>?) {
        items = itemsList
        finishedList?.let {
            itemsFinished = it
        }
        notifyDataSetChanged()
    }

    fun setCategories(newList: List<CategoryX>) {
        categories = newList
        notifyDataSetChanged()

    }

    interface OnQuizItemClickListener {
        fun onItemClick(position: Int)
    }

    internal class CategoryViewHolder(
        itemView: View,
        private val listenerItem: OnQuizItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val numberItemName: TextView = itemView.categoryItem_itemName
        private val rootLayout: CardView = itemView.categoryItem_root


        init {
            rootLayout.setOnClickListener { view ->
                val position = adapterPosition
                listenerItem.onItemClick(position)
            }
        }

        fun bind(category: CategoryX) {
            numberItemName.text = category.name
        }

    }

    inner class QuizViewHolder(
        itemView: View,
        private val listenerItem: OnQuizItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val numberItemName: TextView = itemView.quizItem_itemName
        private val numberItemImageView: ImageView = itemView.quizItem_topImage
        private val rootLayout: CardView = itemView.quizItem_root
        private val groupFinished = itemView.quizItem_previousAttempGroup
        private val dateAttempted = itemView.quizItem_dateAttempted
        private val previousScore = itemView.quizItem_previousAttemptScore
        private val unfinished = itemView.quizItem_unfinished


        init {
            rootLayout.setOnClickListener { view ->
                val position = adapterPosition
                listenerItem.onItemClick(position)
            }
        }

        fun bind(item: Item, quizDetails: QuizDetails?) {
            numberItemName.text = item.title
            Picasso.with(context)
                .load(split(item.mainPhoto.url))
                .into(numberItemImageView)

            quizDetails?.let {
                groupFinished.visibility = View.VISIBLE
                dateAttempted.text = convertLongToTime(it.finishedDate!!)
                previousScore.text = it.previousScore.toString()
                if(it.unfinished!!) {
                    unfinished.visibility = View.VISIBLE
                    previousScore.visibility = View.INVISIBLE
                }
            }


        }

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }
    }

    /*inner class ViewHolder(
        private val binding: QuizItemBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val listenerItem: ViewHolderClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private val text1: TextView =

        fun bind(item: Item){
            binding.quizItem = item
//            binding.lifecycleOwner = lifecycleOwner
            binding.quizItemListener = listenerItem
            binding.executePendingBindings()

        }
    }*/
}