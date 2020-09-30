data class Note (
        val title: String,
        val text: String,
        val id: Int? = null,
) {
    val comments = mutableListOf<Comment>()
    val date: Long = java.time.Instant.now().toEpochMilli()
    val isDeleted: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other !is Note) return false
        return title == other.title && text == other.text
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}