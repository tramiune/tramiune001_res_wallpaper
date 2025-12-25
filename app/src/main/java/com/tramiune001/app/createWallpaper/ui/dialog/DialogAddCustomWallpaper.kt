package com.tramiune001.app.createWallpaper.ui.dialog

import android.view.Gravity
import androidx.fragment.app.activityViewModels
import com.tramiune001.app.createWallpaper.base.BaseDialogFragmentWallpaper
import com.tramiune001.app.createWallpaper.data.Media
import com.tramiune001.app.createWallpaper.data.getStillMedia
import com.tramiune001.app.createWallpaper.databinding.DialogAddCustomWallpaperBinding
import com.tramiune001.app.createWallpaper.ui.MainViewModel
import com.tramiune001.app.createWallpaper.ui.image.adapter.AddCustomWallpaperAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DialogAddCustomWallpaper : BaseDialogFragmentWallpaper<DialogAddCustomWallpaperBinding>() {
    override val dialogGravity: Int
        get() = Gravity.BOTTOM


    var onItemAddClicked: ((Media) -> Unit)? = null

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val addCustomWallpaper = AddCustomWallpaperAdapter(
        onAddItemClicked = { item ->
            Timber.d("onAddItemClicked: $item")
            onItemAddClicked?.invoke(item.getStillMedia())
            dismiss()
        }
    )

    override fun initDialog() {
        binding.rcvWallpaper.apply {
            adapter = addCustomWallpaper
        }
//        val stillWallpapers = sharedViewModel.homeUiState.value.wallpapers
//            ?.filter { it.type == Type.STILL }
//        Timber.d("stillWallpapers: $stillWallpapers")
//        addCustomWallpaper.submitList(stillWallpapers)
    }

}
