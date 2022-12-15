package com.example.jpgtopngapp.presenter

import android.content.res.AssetManager
import com.example.jpgtopngapp.model.MyLoader
import com.example.jpgtopngapp.ui.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainPresenter(private val viewState: MainView) {

    private val model = MyLoader()
    private val file = "jpg/nature.jpg"
    private var disposable: Disposable? = null


    fun start(assetManager: AssetManager) {
        viewState.printLog("Начинаем загрузку $file")
        viewState.showLoading(true)

        val maybe = model.loadJpgFromAsset(assetManager, file)

        disposable = maybe
            .subscribeByDefault()
            .subscribe(
                {//onSuccess
                    viewState.showImage(it)
                    viewState.printLog("Успешно завершена загрузка $file")
                    viewState.showLoading(false)
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