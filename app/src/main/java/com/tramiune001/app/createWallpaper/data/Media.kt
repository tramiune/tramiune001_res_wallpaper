package com.tramiune001.app.createWallpaper.data

import java.io.Serializable

data class Media(
    val contentType: String?,
    val url: String?,
    val thumbUrl: String?,
    val name: String?,
    val hour: String?,
) : Serializable

val Media.thumbOrUrl: String
    get() = (if (thumbUrl.isNullOrEmpty()) url else thumbUrl).toString()
