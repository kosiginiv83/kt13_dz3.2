class Container(id: Int): Crudable, Restorable {
    val id: Int = id
    val notes: MutableList<Note> = mutableListOf()
    private var notesCount = 0
    private var commentsCount = 0

    override fun <T> add(element: T, noteId: Int?) {
        when (element) {
            is Container -> throw IllegalStateException("Нельзя добавить контейнер в контейнер")
            is Note -> {
                if (notes.any { it == element && it.isDeleted }) throw
                            ElementIsDeletedException("Идентичная записка находится в корзине контейнера с id: $id")
                if (notes.any { it == element }) throw
                            ElementAlreadyExistException("Идентичная записка уже существует в контейнере с id: $id")
                notes.add(element.copy(id = ++notesCount))
                println("Записка c id: $notesCount была добавлена в контейнер с id: $id")
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При добавлении комментария нужно указать id записки")
                val foundNoteIndex = notes.indexOfFirst { it.id == noteId }
                when (foundNoteIndex) {
                    -1 -> throw ElementNotFoundException("Нет записки с таким id")
                    else -> {
                        if (notes[foundNoteIndex].isDeleted) throw
                        ElementIsDeletedException("Записка с id: $noteId удалена")
                    }
                }
//                if (notes[foundNoteIndex].comments.contains(element)) throw
                if (notes[foundNoteIndex].comments.any { it == element }) throw
                ElementAlreadyExistException("В записке с id: $foundNoteIndex уже есть такой комментарий")
                notes[foundNoteIndex].comments.add(element.copy(id = ++commentsCount))
                println("В записку с id: ${notes[foundNoteIndex].id} добавлен комментарий с id: $commentsCount")
            }
        }
    }

    fun <T> findElement(element: T, list: MutableList<T>): T? = list.find { it == element }
    fun <T> findElementIndex(element: T, list: MutableList<T>): Int = list.indexOf(element)

}