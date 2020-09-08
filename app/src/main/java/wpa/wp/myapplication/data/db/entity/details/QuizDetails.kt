package wpa.wp.myapplication.data.db.entity.details


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz_details")
data class QuizDetails(
    val avgResult: Double,
    val buttonStart: String,
    val canonical: String,
    val categories: List<Category>,
    val category: CategoryX,
    val celebrity: Celebrity,
    val cityAvg: Any,
    val cityCount: Any,
    val cityTime: Any,
    val content: String,
    val created: Int,
    val createdAt: String,
    @PrimaryKey
    val id: Long,
    val isBattle: Boolean,
    val latestResults: List<LatestResult>,
    val mainPhoto: MainPhoto,
    @SerializedName("opinions_enabled")
    val opinionsEnabled: Boolean,
    val publishedAt: String,
    val questions: List<Question>,
    val rates: List<Rate>,
    val resultCount: Int,
    val scripts: String,
    val shareTitle: String,
    val sponsored: Boolean,
    val sponsoredResults: SponsoredResults,
    val tags: List<Tag>,
    val title: String,
    val type: String,
    val userBattleDone: Boolean
)