package com.team.testtask.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(context: Context) {
    val activity = when (context) {
        is Activity -> {
            context
        }

        is ContextWrapper -> {
            context.baseContext as Activity
        }

        else -> {
            null
        }
    }

    activity?.let {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}