abstract class Component(open val id: Int?)


data class Note(
    val title: String,
    val text: String,
    override val id: Int? = null,
) : Component(id) {
    val comments = mutableListOf<Comment>()
    val date: Long = java.time.Instant.now().toEpochMilli()
    var isDeleted: Boolean = false
    private var commentsCount: Int = 0

    fun getNewCommentId(): Int = ++commentsCount

    override fun equals(o: Any?): Boolean {
        val other = o as? Note ?: return false
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
    var isDeleted: Boolean = false

    override fun equals(o: Any?): Boolean {
        val other = o as? Comment ?: return false
        return message == other.message
    }

    override fun hashCode(): Int {
        return message.hashCode()
    }
}
