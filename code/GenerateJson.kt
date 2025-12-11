import java.io.File

object Type {
    const val STILL = 1
    const val LIVE = 0
    const val DOUBLE = 2
    const val DAYS = 3
    const val COUPLE = 4
}

@JvmStatic
fun main(args: Array<String>) {
    val count = 21                      // số item muốn gen (001 -> 021)
    val baseCreatedAt = 1760677055L     // timestamp gốc
    val outFile = File("D:/app_android/other/resources/json/wallpaper.json")
    // ↑ đổi path này sang đúng thư mục của cậu

    val sb = StringBuilder()
    sb.append("[\n")

    for (i in 1..count) {
        val numStr = "%03d".format(i)          // 1 -> "001"
        val createdAt = baseCreatedAt + (i - 1)

        sb.append(
            """
                {
                  "id": "100_popular_$numStr",
                  "medias": [
                    {
                      "url": "https://tramiune.github.io/tramiune001_res_wallpaper/resources/silly_$numStr.mp4",
                      "name": "silly_$numStr.mp4",
                      "thumbUrl": "",
                      "contentType": "video/mp4"
                    },
                    {
                      "url": "https://tramiune.github.io/tramiune001_res_wallpaper/resources/silly_$numStr.webp",
                      "name": "silly_$numStr.webp",
                      "thumbUrl": "https://tramiune.github.io/tramiune001_res_wallpaper/resources/silly_${numStr}_preview.webp",
                      "contentType": "image/jpeg"
                    }
                  ],
                  "categoryId": "100_popular",
                  "isPremium": true,
                  "createdAt": $createdAt,
                  "subType": 0,
                  "type": 0,
                  "coinToUnlock": 0
                }
                """.trimIndent()
        )

        if (i != count) sb.append(",\n")  // thêm dấu , giữa các object
    }

    sb.append("\n]")
    outFile.writeText(sb.toString())

    println("JSON generated at: ${outFile.absolutePath}")
}

