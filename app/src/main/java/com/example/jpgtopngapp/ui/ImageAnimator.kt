package com.example.jpgtopngapp.ui

import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.jpgtopngapp.databinding.ActivityMainBinding

const val DURATION = 1000L

class ImageAnimator(private val binding: ActivityMainBinding) {
    fun prepareAnimateToFullSize() {
        binding.ivImage.animate().alpha(1f).duration = DURATION

        val set = TransitionSet()
        set.addTransition(ChangeBounds())
        set.interpolator = AnticipateOvershootInterpolator(1f)
        set.duration = DURATION
        binding.ivImage.layoutParams.let { params ->
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            binding.ivImage.layoutParams = params
        }
        TransitionManager.beginDelayedTransition(binding.imageContainer, set)
    }

    fun prepareAnimateToSmallSize(width: Int, height: Int) {
        binding.ivImage.animate().alpha(0.5f).duration = DURATION

        val set = TransitionSet()
        set.addTransition(ChangeBounds())
        set.ordering = TransitionSet.ORDERING_SEQUENTIAL
        set.duration = DURATION
        TransitionManager.beginDelayedTransition(binding.imageContainer, set)
        binding.ivImage.layoutParams.let { params ->
            params.height = width
            params.width = height
            binding.ivImage.layoutParams = params
        }
        TransitionManager.beginDelayedTransition(binding.imageContainer, set)
    }
}