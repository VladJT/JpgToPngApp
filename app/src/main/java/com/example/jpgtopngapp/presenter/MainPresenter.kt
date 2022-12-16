package com.example.jpgtopngapp.presenter

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import com.example.jpgtopngapp.model.MyLoader
import com.example.jpgtopngapp.ui.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

const val jpgFileName = "nature.jpg"
const val pngFileName = "nature_converted.png"

class MainPresenter(private val viewState: MainView) {

    private val model = MyLoader()
    private var disposable: Disposable? = null


    fun start(assetManager: AssetManager, filesDir: File) {
        viewState.printLog("Начинаем конвертацию $jpgFileName")
        viewState.showLoading(true)

        val jpgFilePath = "jpg/$jpgFileName"
        val pngFilePath = "$filesDir/$pngFileName"

        disposable =
            model.convertJpgToPng(assetManager, jpgFilePath, pngFilePath)
                .subscribeByDefault()
                .subscribe({ drawable ->
                    viewState.showImage(drawable)
                    viewState.printLog("Конвертация $jpgFileName в $pngFilePath успешно завершена")
                    viewState.showLoading(false)
                }, {
                    viewState.printLog("✍️Ошибка конвертации: ${it.message}")
                    viewState.showLoading(false)
                })
    }


    fun stop() {
        disposable?.let {
            if(!it.isDisposed) {
                it.dispose()
                viewState.printLog("Операция прервана...", isError = true)
            }
        }
        viewState.showLoading(false)
    }

    fun clear(){
        viewState.clearImage()
        viewState.printLog("Данные очищены")
    }


    private fun <T> Maybe<T>.subscribeByDefault(): Maybe<T> {
        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}