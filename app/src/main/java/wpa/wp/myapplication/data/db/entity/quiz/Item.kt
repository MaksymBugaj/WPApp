package wpa.wp.myapplication.data.db.entity.quiz


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import wpa.wp.myapplication.data.db.Converters


data class Item(
    val buttonStart: String,
    val categories: List<Category>?,
    @Embedded(prefix = "categoryX_")
    val category: CategoryX,
    val content: String,
    val createdAt: String,
    val flagResults: List<FlagResult>,
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @Embedded(prefix = "mainPhoto_")
    val mainPhoto: MainPhoto,
    val publishedAt: String,
    val questions: Int,
    val shareTitle: String,
    val sponsored: Boolean,
    val tags: List<Tag>,
    val title: String,
    val type: String
)