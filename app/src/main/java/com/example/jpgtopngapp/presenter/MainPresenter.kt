package com.example.jpgtopngapp.presenter

import android.content.res.AssetManager
import com.example.jpgtopngapp.model.MyLoader
import com.example.jpgtopngapp.ui.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


class MainPresenter(private val viewState: MainView) {

    private val model = MyLoader()
    private val jpg = "jpg/nature.jpg"
    private val png = "nature_converted.png"
    private var disposable: Disposable? = null


    fun start(assetManager: AssetManager, filesDir: File) {
        viewState.printLog("Начинаем загрузку $jpg")
        viewState.showLoading(true)

        val maybe = model.loadJpgFromAsset(assetManager, jpg)

        disposable = maybe
            .subscribeByDefault()
            .subscribe(
                { //onSuccess
                    viewState.showImage(it)
                    viewState.printLog("Успешно завершена загрузка $jpg")
                    viewState.showLoading(false)
                    model.convertDrawableToPng(filesDir, it, png)
                },
                {//onError
                    viewState.printLog("Ошибка загрузки ${it.message}")
                    viewState.showLoading(false)
                })


    }


    fun stop() {
        disposable?.dispose()
        viewState.printLog("Операция прервана...")
        viewState.showLoading(false)
    }

    private fun <T> Maybe<T>.subscribeByDefault(): Maybe<T> {
        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())// для ANDROID
    }


}