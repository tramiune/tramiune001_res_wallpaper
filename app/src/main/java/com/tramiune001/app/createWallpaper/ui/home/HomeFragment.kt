package com.tramiune001.app.createWallpaper.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tramiune001.app.createWallpaper.R
import com.tramiune001.app.createWallpaper.base.BaseFragmentVB
import com.tramiune001.app.createWallpaper.databinding.FragmentHomeBinding
import com.tramiune001.app.createWallpaper.extension.collectInStarted
import com.tramiune001.app.createWallpaper.ui.MainViewModel
import com.tramiune001.app.createWallpaper.ui.home.adapter.CategoryAdapter
import timber.log.Timber

class HomeFragment : BaseFragmentVB<FragmentHomeBinding, HomeViewModel>(
    bindingFactory = FragmentHomeBinding::inflate
) {

    override val classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java
    private val sharedViewModel: MainViewModel by activityViewModels()

    private val categoryAdapter = CategoryAdapter(
        onItemClicked = { categoryItem ->
            sharedViewModel.selectedCategory = categoryItem
            findNavController().navigate(R.id.imageFragment)
        }
    )

    override fun initControl(view: View, savedInstanceState: Bundle?) {
        binding.rcvCategory.adapter = categoryAdapter
        val list = viewModel.getCategoriesFromAssets(requireContext())
        Timber.d("list: $list")
    }

    override fun listener() {
        viewModel.categories.collectInStarted(this@HomeFragment) { categories ->
            categoryAdapter.submitList(categories)
        }
    }


}
