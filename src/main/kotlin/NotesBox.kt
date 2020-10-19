class NotesBox : Crudable, Restorable {
    val notesBox = mutableListOf<Note>()
    private var notesCount: Int = 0

    fun <T> getElement(element: T, list: MutableList<T>): T? = list.find { it == element }
    fun <T> getElementIndex(element: T, list: MutableList<T>): Int = list.indexOf(element)

    fun getNoteById(id: Int): Note = notesBox.find { it.id == id } ?:
            throw ElementNotFoundException("Записка с id: $id не найдена")

    fun getCommentById(id: Int, noteId: Int): Comment =
            getNoteById(noteId).comments.find { it.id == id } ?:
            throw ElementNotFoundException("Комментарий с id: $id к записке с id: $noteId не найден")

    fun get(): MutableList<Note> = notesBox

    fun getComments(noteId: Int): MutableList<Comment> = getNoteById(noteId).comments


    override fun <T> add(element: T, noteId: Int?) {
        when (element) {
            is NotesBox -> throw IllegalStateException("Нельзя добавить контейнер в контейнер")
            is Note -> {
                when (val foundNote = getElement(element, notesBox)) {
                    null -> {
                        notesBox.add(element.copy(id = ++notesCount))
                        println("Записка c id: $notesCount добавлена")
                    }
                    else -> {
                        if (foundNote.isDeleted) throw ElementAlreadyExistException("Идентичная " +
                                "записка (id: ${foundNote.id}) находится в корзине")
                        throw ElementAlreadyExistException("Идентичная записка " +
                                "(id: ${foundNote.id}) уже существует")
                    }
                }
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При добавлении комментария " +
                        "нужно указать id записки")
                val foundNote = getNoteById(noteId)
                if (foundNote.isDeleted) throw
                ElementIsDeletedException("Записка с id: $noteId находится в корзине")
                when (val foundComment = getElement(element, foundNote.comments)) {
                    null -> {
                        val newCommentId = foundNote.getNewCommentId()
                        foundNote.comments.add(element.copy(id = newCommentId))
                        println("В записку с id: $noteId добавлен комментарий с id: $newCommentId")
                    }
                    else -> {
                        if (foundComment.isDeleted) throw ElementIsDeletedException("Идентичный " +
                                "комментарий (id: ${element.id}) находится в корзине (записка " +
                                "с id: $noteId)")
                        throw ElementAlreadyExistException("Идентичный комментарий " +
                                "(id: ${element.id}) уже существует (записка с id: $noteId)")
                    }
                }
            }
        }
    }

    override fun <T> delete(element: T, noteId: Int?) {
        when (element) {
            is NotesBox -> throw IllegalStateException("Нельзя удалить контейнер")
            is Note -> {
                when (val foundNote = getElement(element, notesBox)) {
                    null -> throw ElementNotFoundException("Записка не найдена")
                    else -> {
                        if (foundNote.isDeleted) throw
                        ElementIsDeletedException("Записка с id: ${foundNote.id} уже " +
                                "находится в корзине")
                        foundNote.isDeleted = true
                        println("Записка c id: ${foundNote.id} удалена в корзину")
                    }
                }
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При удалении комментария " +
                        "нужно указать id записки")

            }
        }
    }
}
