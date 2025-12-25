package com.tramiune001.app.createWallpaper.ui.image.uiState

import com.tramiune001.app.createWallpaper.data.WallpaperItem

data class CustomizeUiState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var errorMessage: String? = null,
    var listCustomized: List<WallpaperItem>? = null,
)