package com.tramiune001.app.createWallpaper.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding

abstract class BaseActivityVB<VB : ViewBinding, VM : BaseViewModel>(
    private val bindingFactory: (LayoutInflater) -> VB,
) : AppCompatActivity() {

    protected lateinit var binding: VB
        private set

    protected lateinit var viewModel: VM private set
    protected abstract val classTypeOfViewModel: Class<VM>

    protected val scope: LifecycleCoroutineScope
        get() {
            return lifecycleScope
        }


    var isNavigating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[classTypeOfViewModel]
        binding = bindingFactory(layoutInflater)
        setStatusBarColor(window = window, color = Color.TRANSPARENT)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)
//        binding.root.setTopPaddingEqualToStatusBar()
        initControl(savedInstanceState)
        listener()
    }

    fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                view.setBackgroundColor(color)
                insets
            }
        } else {
            // For Android 14 and below
            window.statusBarColor = color
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (isNavigating) true else super.dispatchTouchEvent(ev)
    }

    protected abstract fun initControl(savedInstanceState: Bundle?)
    abstract fun listener()


}