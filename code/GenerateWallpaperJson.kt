fun main() {

    val animeConfig = CategoryConfig(
        count = 21,
        baseCreatedAt = 1760677055L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/anime/",
        categoryId = "101_anime",
        fileNamePrefix = "anime_",
        liveItemNumbers = (listOf(1, 2, 3, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22) + (46..86)).distinct(),
        previewItemNumbers = listOf(1, 2, 3)
    )

    val sillyConfig = CategoryConfig(
        count = 18,
        baseCreatedAt = 1760678000L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/silly/",
        categoryId = "102_silly",
        fileNamePrefix = "silly_",
        liveItemNumbers = listOf(2, 4, 6, 10, 14),
        previewItemNumbers = listOf(1, 3, 5, 7, 9, 11)
    )

    val allItems = mutableListOf<String>()
    allItems += generateCategoryItems(animeConfig)
    allItems += generateCategoryItems(sillyConfig)

    val finalJson = buildString {
        append("[\n")
        append(allItems.joinToString(",\n"))
        append("\n]")
    }

    println(finalJson)
}


fun generateCategoryItems(config: CategoryConfig): List<String> {
    val items = mutableListOf<String>()

    for (i in 1..config.count) {
        val numStr = "%03d".format(i)
        val createdAt = config.baseCreatedAt + (i - 1)
        val fullFileName = "${config.fileNamePrefix}$numStr"

        val isLive = i in config.liveItemNumbers
        val hasPreview = i in config.previewItemNumbers
        val type = if (isLive) 0 else 1

        val thumbUrl = if (hasPreview)
            "${config.baseUrl}${fullFileName}_preview.webp"
        else
            ""

        val medias = if (isLive) {
            """
            {
              "url": "${config.baseUrl}${fullFileName}.mp4",
              "name": "${fullFileName}.mp4",
              "thumbUrl": "",
              "contentType": "video/mp4"
            },
            {
              "url": "${config.baseUrl}${fullFileName}.webp",
              "name": "${fullFileName}.webp",
              "thumbUrl": "$thumbUrl",
              "contentType": "image/jpeg"
            }
            """
        } else {
            """
            {
              "url": "${config.baseUrl}${fullFileName}.webp",
              "name": "${fullFileName}.webp",
              "thumbUrl": "$thumbUrl",
              "contentType": "image/jpeg"
            }
            """
        }

        val itemJson =
            """
            {
              "id": "${config.categoryId}_$numStr",
              "medias": [$medias],
              "categoryId": "${config.categoryId}",
              "isPremium": true,
              "createdAt": $createdAt,
              "subType": $type,
              "type": $type,
              "coinToUnlock": 0
            }
            """.trimIndent()

        items.add(itemJson)
    }

    return items
}

data class CategoryConfig(
    val count: Int,
    val baseCreatedAt: Long,
    val baseUrl: String,
    val categoryId: String,
    val fileNamePrefix: String,
    val liveItemNumbers: List<Int>,
    val previewItemNumbers: List<Int>
)

