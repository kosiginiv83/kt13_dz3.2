data class Comment(
        val nid: Int,
        val message: String,
        val id: Int? = null,
) {
    val date: Long = java.time.Instant.now().toEpochMilli()
    val isDeleted: Boolean = false
}