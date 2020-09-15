package wpa.wp.myapplication.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import wpa.wp.myapplication.R

fun showDialog(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(R.string.no_internet)
    builder.setMessage(context.resources.getString(R.string.no_internet_message))
    builder.setNeutralButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}

