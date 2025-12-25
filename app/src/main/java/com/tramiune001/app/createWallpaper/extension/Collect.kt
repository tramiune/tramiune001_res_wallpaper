package com.tramiune001.app.createWallpaper.extension

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> Flow<T>.collectInStarted(
    fragment: Fragment,
    collector: suspend (T) -> Unit
) {
    fragment.viewLifecycleOwner.lifecycleScope.launch {
        fragment.viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectInStarted.collect { collector(it) }
        }
    }
}

fun View.setOnDebounceClick(onClick: (v: View) -> Unit) {
    this.setOnClickListener(OnDebounceClickListener.wrap(false, onClick))
}

fun DialogFragment.showIfNotExists(fm: FragmentManager) {
    val tag = this::class.java.simpleName

    val existing = fm.findFragmentByTag(tag)
    if (existing == null || !existing.isAdded) {
        this.show(fm, tag)
    }
}

fun FragmentManager.dismissIfExist(tag: String) {
    val fragment = findFragmentByTag(tag)
    if (fragment is DialogFragment && fragment.dialog?.isShowing == true) {
        fragment.dismissAllowingStateLoss()
    }
}
