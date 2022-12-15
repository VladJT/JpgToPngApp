package com.example.jpgtopngapp.model

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import io.reactivex.rxjava3.core.Maybe
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit


const val DELAY = 3_000L

class MyLoader {

    fun loadJpgFromAsset(assetManager: AssetManager, file: String): Maybe<Drawable> =
        Maybe.create<Drawable> { emitter ->
            try {
                val ims: InputStream = assetManager.open(file)
                val d = Drawable.createFromStream(ims, null)
                ims.close()
                emitter.onSuccess(d)
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }.delay(DELAY, TimeUnit.MILLISECONDS)

    fun convertDrawableToPng(filesDir: File, jpg: Drawable, png: String) {
        try {
            val f = File(filesDir, png)
            f.createNewFile()
            val out = FileOutputStream(f)
            jpg.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, out) //100-best quality
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}