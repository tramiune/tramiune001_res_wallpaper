package com.tramiune001.app.createWallpaper.data


import java.io.Serializable

data class WallpaperItem(
    val categoryId: String,
    val isPremium: Boolean,
    val medias: List<Media>,
    val id: String,
    var type: Int = Type.LIVE,
    var subType: Int = 0,
    var isSelected: Boolean = false,
    var selecting: Boolean = false
) : Serializable


fun WallpaperItem.getStillMedia(): Media {
    val still = medias.firstOrNull { it.contentType?.startsWith("image") == true }

    return still
        ?: medias.firstOrNull()
        ?: error("WallpaperItem $id không có media nào")
}
