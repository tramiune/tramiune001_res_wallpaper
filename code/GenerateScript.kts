import java.io.File

fun main() {

    println("hello i am finn")

    val animeConfig = CategoryConfig(
        count = 86,
        baseCreatedAt = 1760677055L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/anime/",
        categoryId = "101_anime",
        fileNamePrefix = "anime_",
        liveItemNumbers = (listOf(1, 2, 3) + (9..22) + (46..86)).distinct(),
        previewItemNumbers = listOf(1, 2, 3)
    )

    val sillyConfig = CategoryConfig(
        count = 24,
        baseCreatedAt = 1760678000L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/silly/",
        categoryId = "102_silly_smile",
        fileNamePrefix = "silly_",
        liveItemNumbers = (listOf(1) + (2..24)).distinct(),
        previewItemNumbers = listOf(1, 2)
    )

    val superHero = CategoryConfig(
        count = 3,
        baseCreatedAt = 1760678000L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/superHero/",
        categoryId = "103_super_hero",
        fileNamePrefix = "super_hero_",
        liveItemNumbers = listOf(1, 2, 3),
        previewItemNumbers = emptyList()
    )

    val animal = CategoryConfig(
        count = 15,
        baseCreatedAt = 1760678000L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/animal/",
        categoryId = "105_animal",
        fileNamePrefix = "animal_",
        liveItemNumbers = (listOf(1) + (5..15)).distinct(),
        previewItemNumbers = emptyList()
    )

    val technology = CategoryConfig(
        count = 12,
        baseCreatedAt = 1760678000L,
        baseUrl = "https://cdn.jsdelivr.net/gh/tramiune/tramiune001_res_wallpaper/resources/technology/",
        categoryId = "106_technology",
        fileNamePrefix = "technology_",
        liveItemNumbers = (listOf(1) + (2..12)).distinct(),
        previewItemNumbers = emptyList()
    )

    val allItems = mutableListOf<String>()
    allItems += generateCategoryItems(animeConfig)
    allItems += generateCategoryItems(sillyConfig)
    allItems += generateCategoryItems(superHero)
    allItems += generateCategoryItems(animal)
    allItems += generateCategoryItems(technology)

    val finalJson = buildString {
        append("[\n")
        append(allItems.joinToString(",\n"))
        append("\n]")
    }

    // =======================
    // 👉 GHI RA FILE Ở ROOT PROJECT
    // =======================
    val outputDir = File("D:/app_android/output")
    if (!outputDir.exists()) {
        outputDir.mkdirs() // Tạo thư mục nếu nó chưa tồn tại
    }

    val outputFile = File("D:/app_android/output", "wallpapers_all.json")

    outputFile.writeText(finalJson)

    println("✅ JSON generated successfully!")
    println("📄 File path: ${outputFile.absolutePath}")
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
