package com.tramiune001.app.createWallpaper.ui.image.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.databinding.ItemCustomizeBinding

class CustomizeAdapter(
    private val onAddItemClicked: (WallpaperItem) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
) : ListAdapter<WallpaperItem, CustomizeViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomizeViewHolder {
        val binding = ItemCustomizeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CustomizeViewHolder(binding, onAddItemClicked, onDeleteClicked)
    }


    override fun onBindViewHolder(holder: CustomizeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MediaDiffCallback : DiffUtil.ItemCallback<WallpaperItem>() {
    override fun areItemsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean {
        return oldItem == newItem
    }
}
