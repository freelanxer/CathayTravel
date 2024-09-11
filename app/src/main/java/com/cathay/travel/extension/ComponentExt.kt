package com.cathay.travel.extension

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun Activity.onBackButtonPressed(callback: (() -> Boolean)) {
    (this as? FragmentActivity)?.onBackPressedDispatcher?.addCallback(
        owner = this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!callback()) {
                    remove()
                    performBackPress()
                }
            }
        })
}

fun Activity.performBackPress() {
    (this as? FragmentActivity)?.onBackPressedDispatcher?.onBackPressed()
}

fun Fragment.onBackButtonPressed(callback: () -> Boolean) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!callback()) {
                    remove()
                    performBackPress()
                }
            }
        })
}

fun Fragment.performBackPress() {
    requireActivity().onBackPressedDispatcher.onBackPressed()
}