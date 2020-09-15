package wpa.wp.myapplication.data.db.entity.quiz


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "category_table")
data class Category(
    val name: String,
    val secondaryCid: String,
    val type: String,
    @PrimaryKey
    val uid: Long
)