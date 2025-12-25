package com.tramiune001.app.createWallpaper.ui.image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.tramiune001.app.createWallpaper.base.BaseFragmentVB
import com.tramiune001.app.createWallpaper.databinding.FragmentImageBinding
import com.tramiune001.app.createWallpaper.extension.collectInStarted
import com.tramiune001.app.createWallpaper.extension.showIfNotExists
import com.tramiune001.app.createWallpaper.ui.MainViewModel
import com.tramiune001.app.createWallpaper.ui.dialog.DialogAddCustomWallpaper
import com.tramiune001.app.createWallpaper.ui.image.adapter.CustomizeAdapter
import timber.log.Timber

class ImageFragment : BaseFragmentVB<FragmentImageBinding, ImageViewModel>(
    bindingFactory = FragmentImageBinding::inflate
) {

    override val classTypeOfViewModel: Class<ImageViewModel> = ImageViewModel::class.java

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val customizeAdapter = CustomizeAdapter(
        onAddItemClicked = {
            val dialogAddCustomWallpaper = DialogAddCustomWallpaper()
            dialogAddCustomWallpaper.onItemAddClicked = { item ->
//                viewModel.onAddItem(item)
            }
            dialogAddCustomWallpaper.showIfNotExists(parentFragmentManager)
        },
        onDeleteClicked = {
//            viewModel.onDeleteItem(it)
        }
    )

    override fun initControl(view: View, savedInstanceState: Bundle?) {
        binding.rcvWallpaper.adapter = customizeAdapter
        viewModel.getWallpapersFromAssets(requireContext(), "resources/${sharedViewModel.selectedCategory.id}")
    }

    override fun listener() {
        viewModel.customizeUiState.collectInStarted(this@ImageFragment) { customizes ->
            Timber.d("customizes: ${customizes.listCustomized}")
            customizeAdapter.submitList(customizes.listCustomized)
        }

    }


}
