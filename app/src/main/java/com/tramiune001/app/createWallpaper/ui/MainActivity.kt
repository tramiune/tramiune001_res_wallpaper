package com.tramiune001.app.createWallpaper.ui

import android.os.Bundle
import com.tramiune001.app.createWallpaper.base.BaseActivityVB
import com.tramiune001.app.createWallpaper.databinding.ActivityMainBinding

class MainActivity : BaseActivityVB<ActivityMainBinding, MainViewModel>(
    bindingFactory = ActivityMainBinding::inflate
) {

    override val classTypeOfViewModel: Class<MainViewModel> = MainViewModel::class.java

    override fun initControl(savedInstanceState: Bundle?) {
    }

    override fun listener() {
    }
}
