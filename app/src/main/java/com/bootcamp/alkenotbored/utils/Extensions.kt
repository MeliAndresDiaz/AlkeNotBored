package com.bootcamp.alkenotbored.utils

import android.app.AlertDialog
import android.content.Context

fun Context.showAlert(titleId: Int, messageId: String) {
    AlertDialog.Builder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setPositiveButton("Ok") { _, _ -> }
        .create()
        .show()
}