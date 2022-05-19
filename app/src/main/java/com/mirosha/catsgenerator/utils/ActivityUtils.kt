package com.mirosha.catsgenerator.utils

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

object ActivityUtils {

    fun <T : ViewDataBinding?> AppCompatActivity.getViewBinding(@LayoutRes resId: Int): T =
        DataBindingUtil.setContentView<T>(this, resId)
}