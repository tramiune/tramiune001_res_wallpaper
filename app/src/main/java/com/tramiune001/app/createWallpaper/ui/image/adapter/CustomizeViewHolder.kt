package com.tramiune001.app.createWallpaper.ui.image.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.data.getStillMedia
import com.tramiune001.app.createWallpaper.databinding.ItemCustomizeBinding
import com.tramiune001.app.createWallpaper.extension.setOnDebounceClick
import com.tramiune001.app.createWallpaper.utils.Constant
import com.tramiune001.app.createWallpaper.utils.GlideHelper

class CustomizeViewHolder(
    private val binding: ItemCustomizeBinding,
    private val onAddItemClicked: (WallpaperItem) -> Unit,
    private val onDeleteItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WallpaperItem) {
        binding.apply {
            root.setOnDebounceClick {
                if (item.getStillMedia().contentType == Constant.ADD_MORE_ID) {
                    onAddItemClicked(item)
                }
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
            icDelete.setOnDebounceClick {
                onDeleteItemClicked(layoutPosition)
            }
        }
    }
}
