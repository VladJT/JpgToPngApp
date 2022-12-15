package com.example.jpgtopngapp.ui

import android.graphics.drawable.Drawable
import android.text.SpannableString

interface MainView {
    fun showImage(drawable: Drawable)
    fun printLog(text: String, isError: Boolean = false)
    fun showLoading(isLoading: Boolean)
    fun clearImage()
}