package com.tramiune001.app.createWallpaper.data

import android.os.Parcelable

data class Category(
    val id: String,
    val title: String,
    val imageUrl: String,
    val createdAt: Long
)
