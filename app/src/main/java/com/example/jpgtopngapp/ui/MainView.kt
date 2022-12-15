package com.example.jpgtopngapp.ui

import android.graphics.drawable.Drawable

interface MainView {
    fun showImage(drawable: Drawable)
    fun printLog(text: String)

    fun showLoading(isLoading: Boolean)
}