package com.example.jpgtopngapp.model

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit


const val DELAY = 1_000L

class MyLoader {

    fun convertJpgToPng(
        assetManager: AssetManager,
        jpgFilePath: String,
        pngFilePath: String
    ): Maybe<Drawable> =
        Completable.create { emitter ->
            try {
                val inputStream = assetManager.open(jpgFilePath)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                val file = File(pngFilePath).apply { createNewFile() }
                FileOutputStream(file).also {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) //100-best quality
                    it.close()
                }
                emitter.onComplete()
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }.andThen(
            Maybe.create<Drawable> { emitter ->
                try {
                    val inputStream: InputStream = assetManager.open(jpgFilePath)
                    val drawable = Drawable.createFromStream(inputStream, null)
                    inputStream.close()
                    emitter.onSuccess(drawable)
                } catch (e: IOException) {
                    emitter.onError(e)
                }
            }.delay(DELAY, TimeUnit.MILLISECONDS)
        )
}