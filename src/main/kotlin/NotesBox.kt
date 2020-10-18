class NotesBox : Crudable, Restorable {
    val notesBox = mutableListOf<Note>()
    private var notesCount: Int = 0


    fun <T> findElement(element: T, list: MutableList<T>): T? = list.find { it == element }
    fun <T> findElementIndex(element: T, list: MutableList<T>): Int = list.indexOf(element)


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
//                when (val foundNote = notesBox.find { it == element }) {
                when (val foundNote = findElement(element, notesBox)) {
                    null -> {
                        notesBox.add(element.copy(id = ++notesCount))
                        println("Записка c id: $notesCount была добавлена")
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
//                val foundNoteIndex = notesBox.indexOf(element)
                val foundNoteIndex = findElementIndex(element, notesBox[noteId].comments)
                when (foundNoteIndex) {
                    -1 -> throw ElementNotFoundException("Нет записки с таким id")
                    else -> {
                        if (notesBox[foundNoteIndex].isDeleted) throw
                                ElementIsDeletedException("Записка с id: $foundNoteIndex удалена")
                    }
                }
                if (notesBox[foundNoteIndex].comments.contains(element)) throw
                        ElementAlreadyExistException("В записке с id: ${notesBox[foundNoteIndex].id} уже есть такой комментарий")
                val newCommentId = getNoteById(noteId)?.getIncreasedCommentId()
                notesBox[foundNoteIndex].comments.add(element.copy(id = newCommentId))
                println("В записку с id: ${notesBox[foundNoteIndex].id} добавлен комментарий с id: $newCommentId")
            }
        }
    }

    override fun <T> delete(element: T, noteId: Int?) {
        when (element) {
            is NotesBox -> throw IllegalStateException("Нельзя удалить контейнер")
            is Note -> {
                when (val foundNoteIndex = notesBox.indexOf(element)) {
                    -1 -> throw
                            ElementNotFoundException("Отсутствует записка с id: ${element.id}")
                    else -> {
                        if (notesBox[foundNoteIndex].isDeleted) throw
                                ElementIsDeletedException("Данная записка уже находится в корзине")
                        notesBox[foundNoteIndex].isDeleted = true
                        println("Записка c id: ${notesBox[foundNoteIndex].id} была удалена в корзину")
                    }
                }
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При удалении комментария нужно указать id записки")
                val foundNoteIndex = notesBox.indexOf(element)
            }
        }
    }
}
