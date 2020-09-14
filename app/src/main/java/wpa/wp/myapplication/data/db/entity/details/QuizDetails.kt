package wpa.wp.myapplication.data.db.entity.details


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz_details")
data class QuizDetails(
    @Expose( serialize = true, deserialize = true) val avgResult: Double?,
    @Expose( serialize = true, deserialize = true) val buttonStart: String?,
    @Expose( serialize = true, deserialize = true) val canonical: String?,
    @Expose( serialize = true, deserialize = true) val categories: List<Category>?,
    @Embedded(prefix = "categoryX_")
    @Expose( serialize = true, deserialize = true) val category: CategoryX?,
    @Embedded(prefix = "celebrity_")
    @Expose( serialize = true, deserialize = true) val celebrity: Celebrity?,
    @Expose( serialize = true, deserialize = true) val cityAvg: Double? = null,
    @Expose( serialize = true, deserialize = true) val cityCount: Double? = null,
    @Expose( serialize = true, deserialize = true) val cityTime: String? = null,
    @Expose( serialize = true, deserialize = true) val content: String?,
    @Expose( serialize = true, deserialize = true) val created: Int?,
    @Expose( serialize = true, deserialize = true) val createdAt: String?,
    @PrimaryKey
    @Expose( serialize = true, deserialize = true) val id: Long?,
    @Expose( serialize = true, deserialize = true) val isBattle: Boolean?,
    @Expose( serialize = true, deserialize = true) val latestResults: List<LatestResult>?,
    @Embedded(prefix = "mainPhoto_")
    @Expose( serialize = true, deserialize = true) val mainPhoto: MainPhoto?,
    @SerializedName("opinions_enabled")
    @Expose( serialize = true, deserialize = true) val opinionsEnabled: Boolean?,
    @Expose( serialize = true, deserialize = true) val publishedAt: String?,
    @Expose( serialize = true, deserialize = true) val questions: List<Question>?,
    @Expose( serialize = true, deserialize = true) val rates: List<Rate>?,
    @Expose( serialize = true, deserialize = true) val resultCount: Int?,
    @Expose( serialize = true, deserialize = true)val scripts: String?,
    @Expose( serialize = true, deserialize = true) val shareTitle: String?,
    @Expose( serialize = true, deserialize = true)val sponsored: Boolean?,
    @Embedded(prefix = "sponsoredResults_")
    @Expose( serialize = true, deserialize = true)val sponsoredResults: SponsoredResults?,
    @Expose( serialize = true, deserialize = true)val tags: List<Tag>?,
    @Expose( serialize = true, deserialize = true)val title: String?,
    @Expose( serialize = true, deserialize = true)val type: String?,
    @Expose( serialize = true, deserialize = true)val userBattleDone: Boolean?,
    @Expose( serialize = true, deserialize = true)val userAnswers: Map<String,String>?,
    @Expose( serialize = true, deserialize = true) val finishedDate: Long?,
    @Expose( serialize = true, deserialize = true)val unfinished: Boolean?,
    @Expose( serialize = true, deserialize = true)val previousScore: Int?

)