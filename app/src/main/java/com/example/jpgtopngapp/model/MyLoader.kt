package com.example.jpgtopngapp.model

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import io.reactivex.rxjava3.core.Maybe
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


}