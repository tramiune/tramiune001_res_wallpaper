package com.tramiune001.app.createWallpaper.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tramiune001.app.createWallpaper.data.Category
import com.tramiune001.app.createWallpaper.databinding.ItemCategoryBinding
import com.tramiune001.app.createWallpaper.utils.GlideHelper

class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val onItemClicked: (Category) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Category) {
        binding.apply {
            GlideHelper.loadIntoImageView(
                binding.root.context,
                item.imageUrl,
                binding.icFlag,
                onSuccess = { },
                onLoading = { },
                onError = { },
            )
            tvCountryName.text = item.title
            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }


}
