package com.example.jpgtopngapp

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.jpgtopngapp.databinding.ActivityMainBinding
import com.example.jpgtopngapp.presenter.MainPresenter
import com.example.jpgtopngapp.ui.ImageAnimator
import com.example.jpgtopngapp.ui.MainView
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val logArray = mutableListOf<Any>()
    private var adapter: ArrayAdapter<Any>? = null
    private val imageAnimator: ImageAnimator by lazy { ImageAnimator(binding) }
    private val presenter: MainPresenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        // для ошибки с dispose (UndeliverableException)!!
        RxJavaPlugins.setErrorHandler {
            printLog("UndeliverableException: ${it.message}", isError = true)
        }

        binding.btnStart.setOnClickListener { presenter.start(assets, applicationContext.filesDir) }
        binding.btnStop.setOnClickListener { presenter.stop() }
        binding.btnClear.setOnClickListener { presenter.clear() }

        adapter = ArrayAdapter<Any>(
            this,
            android.R.layout.simple_list_item_1, logArray
        )
        binding.tvLog.adapter = adapter
        showLoading(false)
    }

    override fun showImage(drawable: Drawable) {
        imageAnimator.prepareAnimateToFullSize()
        binding.ivImage.setImageDrawable(drawable)
    }

    override fun clearImage() {
        imageAnimator.prepareAnimateToSmallSize(
            resources.getDimensionPixelSize(R.dimen.image_size_small),
            resources.getDimensionPixelSize(R.dimen.image_size_small)
        )
        binding.ivImage.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_insert_photo_24))
    }

    override fun printLog(text: String, isError: Boolean) {
        val message = "${SimpleDateFormat("hh:mm:ss").format(Date())} $text"
        if (isError) {
            logArray.add(message.toRedBoldString())
        } else {
            logArray.add(message)
        }
        adapter?.notifyDataSetChanged()
        binding.tvLog.smoothScrollToPosition(logArray.size)
    }


    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
            binding.btnStop.isEnabled = true
            binding.btnClear.isEnabled = false
            binding.btnStart.isEnabled = false
        } else {
            binding.loadingBar.visibility = View.GONE
            binding.btnStop.isEnabled = false
            binding.btnClear.isEnabled = true
            binding.btnStart.isEnabled = true
        }
    }


    private fun String.toRedBoldString(): SpannableString = SpannableString(this)
        .apply {
            setSpan(
                ForegroundColorSpan(Color.RED),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
}
