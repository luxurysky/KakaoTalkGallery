package luxurysky.kakaotalkgallery.util

object StringUtils {

    private const val BYTES = "Byte"
    private const val KILOBYTES = "KB"
    private const val MEGABYTES = "MB"
    private const val GIGABYTES = "GB"

    private const val KILO = 1024
    private const val MEGA = KILO * 1024 // 1048576
    private const val GIGA = MEGA * 1024 // 1073741824

    fun convertFileSize(fileSize: Long): String {
        return when {
            fileSize < KILO -> "$fileSize $BYTES"
            fileSize < MEGA -> "${fileSize / KILO} $KILOBYTES"
            fileSize < GIGA -> "${fileSize / MEGA} $MEGABYTES"
            else -> "${fileSize / GIGA} $GIGABYTES"
        }
    }
}