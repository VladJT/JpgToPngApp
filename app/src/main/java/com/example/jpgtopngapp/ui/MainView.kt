package com.example.jpgtopngapp.ui

import android.graphics.drawable.Drawable
import android.text.SpannableString

interface MainView {
    fun showImage(drawable: Drawable)
    fun clearImage()
    fun printLog(text: String, isError: Boolean = false)
    fun showLoading(isLoading: Boolean)
}