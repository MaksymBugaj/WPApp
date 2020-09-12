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
import timber.log.Timber
import wpa.wp.myapplication.R
import wpa.wp.myapplication.data.db.entity.quiz.Category
import wpa.wp.myapplication.data.db.entity.quiz.CategoryX
import wpa.wp.myapplication.data.db.entity.quiz.Item
import wpa.wp.myapplication.util.split


class QuizAdapter(
    private val context: Context,
    private val isCategory: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Item> = emptyList()
    private var categories: List<CategoryX> = emptyList()
    private lateinit var listener:OnQuizItemClickListener

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
        return when(isCategory){
            true -> {
                val layout =
                    LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
                CategoryViewHolder(layout,listener)
            }
            else -> {
                val layout =
                    LayoutInflater.from(parent.context).inflate(R.layout.quiz_item, parent, false)
                QuizViewHolder(layout,listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return when(isCategory){
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
        when(isCategory){
            true -> {(holder as CategoryViewHolder).bind(categories[position])}
            false -> {(holder as QuizViewHolder).bind(items[position])}

        }
    }

    fun initListener(listener: OnQuizItemClickListener) {
        this.listener = listener
    }

    fun setItems(newList: List<Item>){
        items = newList
        notifyDataSetChanged()
    }

    fun setCategories(newList: List<CategoryX>){
        categories = newList
        notifyDataSetChanged()

    }

    interface OnQuizItemClickListener {
        fun onItemClick(position: Int)
    }

    internal class CategoryViewHolder(
        itemView: View,
        private val listenerItem: OnQuizItemClickListener
    ): RecyclerView.ViewHolder(itemView){

        private val numberItemName: TextView = itemView.categoryItem_itemName
        private val numberItemImageView: ImageView = itemView.categoryItem_topImage
        private val rootLayout: CardView = itemView.categoryItem_root


        init {
            rootLayout.setOnClickListener { view ->
                val position = adapterPosition
                listenerItem.onItemClick(position)
            }
        }

        fun bind(itme: CategoryX){
            numberItemName.text = itme.name
            /*Picasso.with(context)
                .load(split(itme.mainPhoto.url))
                .into(numberItemImageView)

            split(itme.mainPhoto.url)*/
        }

    }

    inner class QuizViewHolder(
        itemView: View,
        private val listenerItem: OnQuizItemClickListener
    ): RecyclerView.ViewHolder(itemView){

        private val numberItemName: TextView = itemView.quizItem_itemName
        private val numberItemImageView: ImageView = itemView.quizItem_topImage
        private val rootLayout: CardView = itemView.quizItem_root


        init {
            rootLayout.setOnClickListener { view ->
                val position = adapterPosition
                listenerItem.onItemClick(position)
            }
        }

        fun bind(itme: Item){
            numberItemName.text = itme.title
            Picasso.with(context)
                .load(split(itme.mainPhoto.url))
                .into(numberItemImageView)


        }

        /*private fun split(url: String): String{
            val separated: List<String> = url.split("://")
            separated[0] // this will contain "Fruit"
            separated[1] // this will contain " they taste good"
            val urlCorrectBeginning = "https://i.wpimg.pl/300x/"

            Timber.tag("NOPE").d("urlki: ${separated[1]} : ${urlCorrectBeginning.plus(separated[1])}")

            return urlCorrectBeginning.plus(separated[1])
        }*/

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