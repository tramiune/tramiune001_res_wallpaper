package com.tramiune001.app.createWallpaper.ui.home

import android.os.Bundle
import android.view.View
import com.tramiune001.app.createWallpaper.base.BaseFragmentVB
import com.tramiune001.app.createWallpaper.databinding.FragmentHomeBinding
import com.tramiune001.app.createWallpaper.ui.home.adapter.CategoryAdapter
import timber.log.Timber

class HomeFragment : BaseFragmentVB<FragmentHomeBinding, HomeViewModel>(
    bindingFactory = FragmentHomeBinding::inflate
) {

    override val classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java

    private val categoryAdapter = CategoryAdapter(
        onItemClicked = { languageItem ->

        }
    )

    override fun initControl(view: View, savedInstanceState: Bundle?) {
        binding.rcvCategory.adapter = categoryAdapter
        val list = viewModel.getCategoriesFromAssets(requireContext())
        Timber.d("list: $list")
    }

    override fun listener() {
    }


}
