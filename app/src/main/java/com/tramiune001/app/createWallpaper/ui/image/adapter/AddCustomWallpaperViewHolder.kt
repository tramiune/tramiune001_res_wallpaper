package com.tramiune001.app.createWallpaper.ui.image.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.data.getStillMedia
import com.tramiune001.app.createWallpaper.databinding.ItemAddCustomizeBinding
import com.tramiune001.app.createWallpaper.extension.setOnDebounceClick
import com.tramiune001.app.createWallpaper.utils.Constant
import com.tramiune001.app.createWallpaper.utils.GlideHelper

class AddCustomWallpaperViewHolder(
    private val binding: ItemAddCustomizeBinding,
    private val onAddItemClicked: (WallpaperItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WallpaperItem) {
        binding.apply {
            root.setOnDebounceClick {
                onAddItemClicked(item)
            }
            GlideHelper.loadIntoImageView(
                context = binding.root.context,
                path = item.getStillMedia().url.toString(),
                imageView = binding.image,
                onSuccess = {
                },
                requestOptions = GlideHelper.createRequestOptions(
                    width = Constant.PREVIEW_WIDTH_PX,
                    height = Constant.PREVIEW_HEIGHT_PX
                )
            )
        }
    }
}
