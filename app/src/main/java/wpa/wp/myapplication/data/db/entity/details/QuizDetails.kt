package wpa.wp.myapplication.data.db.entity.details


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz_details")
data class QuizDetails(
    val avgResult: Double,
    val buttonStart: String,
    val canonical: String,
    val categories: List<Category>,
    @Embedded(prefix = "categoryX_")
    val category: CategoryX,
    @Embedded(prefix = "celebrity_")
    val celebrity: Celebrity,
    val cityAvg: Double? = null,
    val cityCount: Double? = null,
    val cityTime: String? = null,
    val content: String,
    val created: Int,
    val createdAt: String,
    @PrimaryKey
    val id: Long,
    val isBattle: Boolean?,
    val latestResults: List<LatestResult>,
    @Embedded(prefix = "mainPhoto_")
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
    @Embedded(prefix = "sponsoredResults_")
    val sponsoredResults: SponsoredResults,
    val tags: List<Tag>,
    val title: String,
    val type: String,
    val userBattleDone: Boolean,
    val userAnswers: Map<String, String>?,
    val finishedDate: Long?,
    val unfinished: Boolean?,
    val previousScore: Int?

)