interface Crudable {

    fun <T> add(element: T, noteId: Int? = null)

    fun <T> delete(element: T, noteId: Int? = null)
}