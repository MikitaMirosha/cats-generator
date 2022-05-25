package com.mirosha.catsgenerator.utils

import android.view.View
import android.widget.EditText

fun View.showView() {
    if (this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}

fun View.hideView() {
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}

fun EditText.clearText() {
    if (this.text.isNotEmpty())
        this.text.clear()
}