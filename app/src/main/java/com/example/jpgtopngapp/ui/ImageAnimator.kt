package com.example.jpgtopngapp.ui

import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.jpgtopngapp.databinding.ActivityMainBinding

class ImageAnimator(val binding: ActivityMainBinding) {

    fun prepareAnimateToFullSize() {
        val imageTransition = ChangeBounds()
        imageTransition.interpolator = AnticipateOvershootInterpolator(1.0f)
        imageTransition.duration = 1500L
        binding.ivImage.layoutParams.let { params ->
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            binding.ivImage.layoutParams = params
        }
        TransitionManager.beginDelayedTransition(binding.imageContainer, imageTransition)
    }

    fun prepareAnimateToSmallSize(width: Int, height: Int) {
        val imageTransition = ChangeBounds()
        imageTransition.duration = 500L
        TransitionManager.beginDelayedTransition(binding.imageContainer, imageTransition)
        binding.ivImage.layoutParams.let { params ->
            params.height = width
            params.width = height
            binding.ivImage.layoutParams = params
        }
    }
}