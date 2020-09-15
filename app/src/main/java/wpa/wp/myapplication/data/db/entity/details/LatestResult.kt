package wpa.wp.myapplication.data.db.entity.details


import com.google.gson.annotations.SerializedName

data class LatestResult(
    val city: Int?,
    @SerializedName("end_date")
    val endDate: String?,
    val resolveTime: Int?,
    val result: Double?
)