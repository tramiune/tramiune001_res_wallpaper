package com.tramiune001.app.createWallpaper.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import timber.log.Timber


// This is for ViewBinding
abstract class BaseFragmentVB<VB : ViewBinding, VM : BaseViewModel>(val bindingFactory: (LayoutInflater) -> VB) :
    Fragment() {

    protected lateinit var viewModel: VM private set
    protected lateinit var binding: VB private set
    protected abstract val classTypeOfViewModel: Class<VM>
    private var isDataInitialized = false

    inline fun safeWithActivity(crossinline block: (Activity) -> Unit) {
        val act = activity
        if (act == null || act.isFinishing || act.isDestroyed) {
            Timber.d("Activity not in valid state, skip showing native ad")
            return
        }
        if (!isAdded || isDetached || isRemoving) return
        if (view == null) return
        block(act)
    }

    abstract fun initControl(view: View, savedInstanceState: Bundle?)
    abstract fun listener()

    protected val scope: LifecycleCoroutineScope
        get() {
            return viewLifecycleOwner.lifecycleScope
        }

    /**
     * Lazy initialization of the permission handler
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = bindingFactory(layoutInflater)
        viewModel = createViewModelLazy(classTypeOfViewModel.kotlin, { viewModelStore }).value
//        updateLanguage()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControl(view, savedInstanceState)
        listener()
        if (!isDataInitialized) {
            initData()
            isDataInitialized = true
        }
    }

    open fun initData() {
    }


}
