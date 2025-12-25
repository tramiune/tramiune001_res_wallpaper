package com.tramiune001.app.createWallpaper.ui.image

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.data.Media
import com.tramiune001.app.createWallpaper.data.Type
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.ui.image.uiState.CustomizeUiState
import com.tramiune001.app.createWallpaper.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ImageViewModel : BaseViewModel() {
    private var _customizeUiState = MutableStateFlow(CustomizeUiState())
    var customizeUiState: StateFlow<CustomizeUiState> = _customizeUiState.asStateFlow()

    // This function reads files from an assets folder and creates WallpaperItem instances
    fun getWallpapersFromAssets(context: Context, folderName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val assetManager = context.assets
            val wallpaperItems = mutableListOf<WallpaperItem>()

            try {
                // List all files in the specified folder within assets
                val files = assetManager.list(folderName)

                files?.forEach { file ->
                    // Here we can filter out the required file types (e.g., .webp, .mp4)
                    if (file.endsWith(".webp") || file.endsWith(".mp4") || file.endsWith(".jpg") || file.endsWith(
                            ".png"
                        )
                    ) {
                        // Create a WallpaperItem from each file
                        val wallpaperItem = WallpaperItem(
                            categoryId = "categoryId",  // Example, replace with actual category ID
                            isPremium = false,  // Example, replace with actual logic for premium status
                            medias = listOf(
                                Media(
                                    "image/webp",
                                    "${Constant.BASE_URL}/$folderName/$file",
                                    "file",
                                    "",
                                    ""
                                )
                            ),
                            id = file,  // Use the file name as the ID
                            type = Type.STILL  // Example: you can adjust this based on file type
                        )
                        wallpaperItems.add(wallpaperItem)
                    }
                }
            } catch (e: IOException) {
                // Handle the error if the folder doesn't exist or can't be accessed
                e.printStackTrace()
            }
            val current = _customizeUiState.value
            _customizeUiState.value = current.copy(
                listCustomized = wallpaperItems,
                isSuccess = false
            )
        }
        // Using AssetManager to access assets

    }
}