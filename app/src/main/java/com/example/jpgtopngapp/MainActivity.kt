package com.example.jpgtopngapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jpgtopngapp.databinding.ActivityMainBinding
import com.example.jpgtopngapp.presenter.MainPresenter
import com.example.jpgtopngapp.ui.MainView
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val presenter: MainPresenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // для ошибки с dispose (UndeliverableException)!!
        RxJavaPlugins.setErrorHandler {
            Toast.makeText(this, "RxJavaPlugins error: ${it.message}", Toast.LENGTH_LONG).show()
        }


        binding.btnStart.setOnClickListener { presenter.start(assets) }
        binding.btnStop.setOnClickListener { presenter.stop() }
    }

    override fun showImage(drawable: Drawable) {
        binding.ivImage.setImageDrawable(drawable)
    }

    override fun printLog(text: String) {
        val t = binding.tvLog.text
        binding.tvLog.text = "$t\n$text"
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.loadingBar.visibility = View.VISIBLE
        else binding.loadingBar.visibility = View.GONE
    }


}
