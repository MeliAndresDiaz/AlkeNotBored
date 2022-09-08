package com.bootcamp.alkenotbored.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Extension function use like reusable for navigate between activities and pass data with intent
 */

inline fun <reified T : Any> Context.navigateTo(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)