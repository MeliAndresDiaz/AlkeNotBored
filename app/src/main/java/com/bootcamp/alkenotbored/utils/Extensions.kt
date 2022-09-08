package com.bootcamp.alkenotbored.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View

// Builder for show an alert message with string
fun Context.showAlert(titleId: Int, messageId: String) {
    AlertDialog.Builder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setPositiveButton("Ok") { _, _ -> }
        .create()
        .show()
}

/// Function used for show views on xml
fun View.show() {
    this.visibility = View.VISIBLE
}

/// Function used for hide views on xml
fun View.hide() {
    this.visibility = View.GONE
}

// Function for validate the price of activity
fun getPriceLevel(activityPrice: String): String {
    return when (activityPrice.toDouble()) {
        0.0 -> "Free"
        in (0.0..0.3) -> "Low"
        in (0.3..0.6) -> "Medium"
        else -> "High"
    }
}