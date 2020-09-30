data class Comment(
        val message: String,
        val id: Int? = null,
) {
    val date: Long = java.time.Instant.now().toEpochMilli()
    val isDeleted: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other !is Comment) return false
        return message == other.message
    }

    override fun hashCode(): Int {
        return message.hashCode()
    }
}