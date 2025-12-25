package com.tramiune001.app.createWallpaper.ui

import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.data.Category

class MainViewModel : BaseViewModel() {
    var selectedCategory = Category(
        id = "",
        title = "",
        imageUrl = "",
        createdAt = 0L
    )
}