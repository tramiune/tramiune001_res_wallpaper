package com.tramiune001.app.createWallpaper.ui.image

import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.ui.image.uiState.CustomizeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImageViewModel : BaseViewModel() {
    private var _customizeUiState = MutableStateFlow(CustomizeUiState())
    var customizeUiState: StateFlow<CustomizeUiState> = _customizeUiState.asStateFlow()


    fun getAll
}