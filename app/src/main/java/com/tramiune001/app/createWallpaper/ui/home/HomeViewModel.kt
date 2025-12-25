package com.tramiune001.app.createWallpaper.ui.home

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.data.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.reflect.Type

class HomeViewModel : BaseViewModel() {

    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    var categories: StateFlow<List<Category>> = _categories.asStateFlow()


    fun getCategoriesFromAssets(context: Context){
        val inputStream = context.assets.open("dev/category.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val categoryListType: Type = object : TypeToken<List<Category>>() {}.type
        _categories.value = gson.fromJson(json, categoryListType)
    }


}