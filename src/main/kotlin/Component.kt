abstract class Component(open val id: Int?)


data class Note(
    val title: String,
    val text: String,
    override val id: Int? = null,
) : Component(id) {
    val comments = mutableListOf<Comment>()
    val date: Long = java.time.Instant.now().toEpochMilli()
    var isDeleted: Boolean = false

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


data class Comment(
    val message: String,
    override val id: Int? = null,
) : Component(id) {
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