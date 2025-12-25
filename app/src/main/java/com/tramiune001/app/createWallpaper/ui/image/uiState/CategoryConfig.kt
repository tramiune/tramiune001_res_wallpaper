package com.tramiune001.app.createWallpaper.ui.image.uiState


data class CategoryConfig(
    val count: Int,
    val baseCreatedAt: Long,
    val baseUrl: String,
    val categoryId: String,
    val fileNamePrefix: String,

    val liveItemNumbers: List<Int> = emptyList(),
    val previewItemNumbers: List<Int> = emptyList(),

    val doubleItems: List<Int> = emptyList(),
    val daysItems: List<Int> = emptyList(),

    val coupleItemsPreview: List<Int> = emptyList(),
    val coupleItemsNoPreview: List<Int> = emptyList(),

    // 👇 NEW
    val orderItems: List<Int> = emptyList()
)
