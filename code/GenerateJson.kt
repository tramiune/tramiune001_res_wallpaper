fun main() {

    val animeConfig = CategoryConfig(
        count = 15,
        baseCreatedAt = 1760677055L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/app/src/main/assets/resources/anime/",
        categoryId = "101_anime",
        fileNamePrefix = "anime_",

        liveItemNumbers = listOf(1, 2, 3),
        previewItemNumbers = listOf(1, 2, 3),

        doubleItems = listOf(5, 6),
        daysItems = listOf(7),

        coupleItemsPreview = listOf(10),
        coupleItemsNoPreview = listOf(11),

        orderItems = listOf(5, 1, 10, 2, 7, 3, 4, 6, 8, 9,15,12,14,13,11)
    )

    val json = buildString {
        append("[\n")
        append(generateCategoryItems(animeConfig).joinToString(",\n"))
        append("\n]")
    }

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


object Type {
    const val STILL = 1
    const val LIVE = 0
    const val DOUBLE = 2
    const val DAYS = 3
    const val COUPLE = 4
    const val ALL = 143
}



