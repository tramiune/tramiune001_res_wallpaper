package com.tramiune001.app.createWallpaper.ui.home

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.data.Category
import java.lang.reflect.Type

class HomeViewModel : BaseViewModel() {
    fun getCategoriesFromAssets(context: Context): List<Category> {
        val inputStream = context.assets.open("dev/category.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val categoryListType: Type = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(json, categoryListType)
    }


}