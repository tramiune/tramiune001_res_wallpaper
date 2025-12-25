package com.tramiune001.app.createWallpaper.ui.image.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.databinding.ItemAddCustomizeBinding

class AddCustomWallpaperAdapter(
    private val onAddItemClicked: (WallpaperItem) -> Unit,
) : ListAdapter<WallpaperItem, AddCustomWallpaperViewHolder>(WallpaperDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddCustomWallpaperViewHolder {
        val binding = ItemAddCustomizeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddCustomWallpaperViewHolder(binding, onAddItemClicked)
    }


    override fun onBindViewHolder(holder: AddCustomWallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WallpaperDiffCallback : DiffUtil.ItemCallback<WallpaperItem>() {
    override fun areItemsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean {
        return oldItem == newItem
    }
}
