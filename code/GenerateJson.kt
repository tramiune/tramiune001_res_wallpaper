fun main() {
    val count = 21
    val baseCreatedAt = 1760677055L

    // THAM SỐ
    val baseUrl = "https://tramiune.github.io/tramiune001_res_wallpaper/resources/anime/"
    val categoryId = "101_anime"
    val fileNamePrefix = "anime_"

    // DANH SÁCH CÁC ITEM LÀ LIVE (type = 0) - CÓ VIDEO
    val liveItemNumbers = listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21)

    // DANH SÁCH CÁC ITEM CÓ PREVIEW (thumbUrl)
    val previewItemNumbers = listOf(1, 2, 4, 6, 8, 12, 16, 20) // Chỉ các item này có preview

    val sb = StringBuilder()
    sb.append("[\n")

    for (i in 1..count) {
        val numStr = "%03d".format(i)
        val createdAt = baseCreatedAt + (i - 1)
        val fullFileName = "${fileNamePrefix}$numStr"

        // Xác định type: nếu i có trong list liveItemNumbers thì type = 0, ngược lại type = 1
        val isLive = i in liveItemNumbers
        val hasPreview = i in previewItemNumbers  // Kiểm tra có preview không
        val type = if (isLive) 0 else 1

        // Tạo thumbUrl: chỉ có nếu item nằm trong previewItemNumbers
        val thumbUrl = if (hasPreview) "${baseUrl}${fullFileName}_preview.webp" else ""

        // Tạo medias dựa trên type
        val medias = if (isLive) {
            // TYPE 0 (Live): Có cả video và image
            """
                {
                  "url": "${baseUrl}${fullFileName}.mp4",
                  "name": "${fullFileName}.mp4",
                  "thumbUrl": "",
                  "contentType": "video/mp4"
                },
                {
                  "url": "${baseUrl}${fullFileName}.webp",
                  "name": "${fullFileName}.webp",
                  "thumbUrl": "$thumbUrl",
                  "contentType": "image/jpeg"
                }
            """
        } else {
            // TYPE 1 (Still): Chỉ có image
            """
                {
                  "url": "${baseUrl}${fullFileName}.webp",
                  "name": "${fullFileName}.webp",
                  "thumbUrl": "$thumbUrl",
                  "contentType": "image/jpeg"
                }
            """
        }

        sb.append(
            """
            {
              "id": "${categoryId}_$numStr",
              "medias": [$medias],
              "categoryId": "$categoryId",
              "isPremium": true,
              "createdAt": $createdAt,
              "subType": $type,
              "type": $type,
              "coinToUnlock": 0
            }
            """.trimIndent()
        )

        if (i != count) sb.append(",\n")
    }

    sb.append("\n]")

    // In kết quả
    println(sb.toString())

    // Thống kê
    val liveCount = liveItemNumbers.size
    val stillCount = count - liveCount
    val previewCount = previewItemNumbers.size

    println("\n📊 THỐNG KÊ:")
    println("   • Total items: $count")
    println("   • Live (type=0): $liveCount items")
    println("   • Still (type=1): $stillCount items")
    println("   • Có preview: $previewCount items")
    println("   • Live items: ${liveItemNumbers.sorted().joinToString(", ")}")
    println("   • Items có preview: ${previewItemNumbers.sorted().joinToString(", ")}")

    // Kiểm tra overlap: items vừa là Live vừa có preview
    val liveWithPreview = liveItemNumbers.intersect(previewItemNumbers.toSet()).sorted()
    println("   • Items vừa Live vừa có preview: ${if (liveWithPreview.isNotEmpty()) liveWithPreview.joinToString(", ") else "Không có"}")
}