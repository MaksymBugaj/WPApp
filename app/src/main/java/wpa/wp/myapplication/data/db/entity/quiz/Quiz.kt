package wpa.wp.myapplication.data.db.entity.quiz


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz_table")
data class Quiz(
    val count: Int,
    val items: List<Item>
)