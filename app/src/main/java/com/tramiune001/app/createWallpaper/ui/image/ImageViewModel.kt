package com.tramiune001.app.createWallpaper.ui.image

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.tramiune001.app.createWallpaper.base.BaseViewModel
import com.tramiune001.app.createWallpaper.data.Media
import com.tramiune001.app.createWallpaper.data.Type
import com.tramiune001.app.createWallpaper.data.WallpaperItem
import com.tramiune001.app.createWallpaper.ui.image.uiState.CategoryConfig
import com.tramiune001.app.createWallpaper.ui.image.uiState.CustomizeUiState
import com.tramiune001.app.createWallpaper.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class ImageViewModel : BaseViewModel() {
    private var _customizeUiState = MutableStateFlow(CustomizeUiState())
    var customizeUiState: StateFlow<CustomizeUiState> = _customizeUiState.asStateFlow()

    // This function reads files from an assets folder and creates WallpaperItem instances
    fun getWallpapersFromAssets(context: Context, folderName: String, categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val assetManager = context.assets
            val wallpaperItems = mutableListOf<WallpaperItem>()

            try {
                // List all files in the specified folder within assets
                val files = assetManager.list(folderName)

                files?.forEach { file ->
                    // Here we can filter out the required file types (e.g., .webp, .mp4)
                    if (file.endsWith(".webp") || file.endsWith(".mp4") || file.endsWith(".jpg") || file.endsWith(
                            ".png"
                        )
                    ) {
                        // Create a WallpaperItem from each file
                        val wallpaperItem = WallpaperItem(
                            categoryId = categoryId,  // Example, replace with actual category ID
                            isPremium = false,  // Example, replace with actual logic for premium status
                            medias = listOf(
                                Media(
                                    "image/webp",
                                    "${Constant.BASE_URL}/$folderName/$file",
                                    "file",
                                    "",
                                    ""
                                )
                            ),
                            id = file,  // Use the file name as the ID
                            type = Type.STILL  // Example: you can adjust this based on file type
                        )
                        wallpaperItems.add(wallpaperItem)
                    }
                }
            } catch (e: IOException) {
                // Handle the error if the folder doesn't exist or can't be accessed
                e.printStackTrace()
            }
            val current = _customizeUiState.value
            _customizeUiState.value = current.copy(
                listCustomized = wallpaperItems,
                isSuccess = false
            )
        }
        // Using AssetManager to access assets

    }

    fun generateJson(baseCreateAt: String, folderName: String, categoryId: String, fileNamePrefix: String, liveItem: String, previewLive: String, double: String, days: String, couple: String, couplePreview: String, orders: String){


        val config = CategoryConfig(
            count = 15,
            baseCreatedAt = baseCreateAt.toLong(),
            baseUrl = "${Constant.BASE_URL}/$folderName/",
            categoryId = categoryId,
            fileNamePrefix = fileNamePrefix,

            liveItemNumbers = parseNumberList(liveItem),
            previewItemNumbers = parseNumberList(previewLive),

            doubleItems = parseNumberList(double),
            daysItems = parseNumberList(days),

            coupleItemsNoPreview = parseNumberList(couple),
            coupleItemsPreview = parseNumberList(couplePreview),

            orderItems = parseNumberList(orders)
        )
        val json = buildString {
            append("[\n")
            append(generateCategoryItems(config).joinToString(",\n"))
            append("\n]")
        }

        saveJsonToResources(categoryId, json)
        println(json)
    }

    fun generateCategoryItems(config: CategoryConfig): List<String> {
        val items = mutableListOf<String>()

        val indexList =
            if (config.orderItems.isNotEmpty())
                config.orderItems
            else
                (1..config.count).toList()

        indexList.forEachIndexed { orderIndex, i ->
            val numStr = "%03d".format(i)
            val createdAt = config.baseCreatedAt + orderIndex
            val id = "${config.categoryId}_$numStr"

            val type: Int
            val medias: String

            when {
                i in config.doubleItems -> {
                    type = Type.DOUBLE
                    medias = """
                {
                  "url": "${config.baseUrl}double_${numStr}_0.webp",
                  "name": "double_${numStr}_0.webp",
                  "thumbUrl": "",
                  "contentType": "image/webp"
                },
                {
                  "url": "${config.baseUrl}double_${numStr}_1.webp",
                  "name": "double_${numStr}_1.webp",
                  "thumbUrl": "",
                  "contentType": "image/webp"
                }
                """
                }

                i in config.daysItems -> {
                    type = Type.DAYS
                    val hours = listOf("06:00", "12:00", "18:00", "24:00")
                    medias = hours.joinToString(",\n") { hour ->
                        val h = hour.substring(0, 2)
                        """
                    {
                      "url": "${config.baseUrl}days_${numStr}_${h}h.webp",
                      "name": "days_${numStr}_${h}h.webp",
                      "thumbUrl": "",
                      "hour": "$hour",
                      "contentType": "image/webp"
                    }
                    """.trimIndent()
                    }
                }

                i in config.coupleItemsPreview || i in config.coupleItemsNoPreview -> {
                    type = Type.COUPLE
                    val hasPreview = i in config.coupleItemsPreview
                    val thumb = if (hasPreview)
                        "${config.baseUrl}couple_${numStr}_preview.webp"
                    else ""

                    medias = """
                {
                  "url": "${config.baseUrl}couple_${numStr}.webp",
                  "name": "couple_${numStr}.webp",
                  "thumbUrl": "$thumb",
                  "contentType": "image/webp"
                }
                """
                }

                i in config.liveItemNumbers -> {
                    type = Type.LIVE
                    val hasPreview = i in config.previewItemNumbers
                    val thumb = if (hasPreview)
                        "${config.baseUrl}${config.fileNamePrefix}${numStr}_preview.webp"
                    else ""

                    medias = """
                {
                  "url": "${config.baseUrl}${config.fileNamePrefix}${numStr}.mp4",
                  "name": "${config.fileNamePrefix}${numStr}.mp4",
                  "thumbUrl": "",
                  "contentType": "video/mp4"
                },
                {
                  "url": "${config.baseUrl}${config.fileNamePrefix}${numStr}.webp",
                  "name": "${config.fileNamePrefix}${numStr}.webp",
                  "thumbUrl": "$thumb",
                  "contentType": "image/webp"
                }
                """
                }

                else -> {
                    type = Type.STILL
                    val hasPreview = i in config.previewItemNumbers
                    val thumb = if (hasPreview)
                        "${config.baseUrl}${config.fileNamePrefix}${numStr}_preview.webp"
                    else ""

                    medias = """
                {
                  "url": "${config.baseUrl}${config.fileNamePrefix}${numStr}.webp",
                  "name": "${config.fileNamePrefix}${numStr}.webp",
                  "thumbUrl": "$thumb",
                  "contentType": "image/webp"
                }
                """
                }
            }

            val itemJson = """
        {
          "id": "$id",
          "medias": [$medias],
          "categoryId": "${config.categoryId}",
          "isPremium": true,
          "createdAt": $createdAt,
          "subType": 0,
          "type": $type,
          "coinToUnlock": 0
        }
        """.trimIndent()

            items.add(itemJson)
        }

        return items
    }

    fun parseNumberList(input: String): List<Int> {
        if (input.isBlank()) return emptyList()

        return input.split(",")
            .flatMap { part ->
                if (part.contains("-")) {
                    val (start, end) = part.split("-").map { it.trim().toInt() }
                    (start..end).toList()
                } else {
                    listOf(part.trim().toInt())
                }
            }
    }

    fun saveJsonToResources(
        categoryId: String,
        json: String
    ) {
        val projectRoot = System.getProperty("user.dir")

        val dir = File("$projectRoot/resources/$categoryId")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "$categoryId.json")
        file.writeText(json)

        println("✅ Saved: ${file.absolutePath}")
    }



}