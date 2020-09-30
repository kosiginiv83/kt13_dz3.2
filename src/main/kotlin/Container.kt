class Container(id: Int): Crudable, Restorable {
    val id: Int = id
    val notes: MutableList<Note> = mutableListOf()
    private var notesCount = 0
    private var commentsCount = 0

    override fun <T> add(elem: T, noteId: Int?) {
        when (elem) {
            is Container -> throw IllegalStateException("Нельзя добавить контейнер в контейнер")
            is Note -> {
                if (notes.any { it == elem && it.isDeleted }) throw
                            NoteAlreadyExistException("Идентичная записка находится в корзине контейнера с id: $id")
                if (notes.any { it == elem }) throw
                            NoteAlreadyExistException("Идентичная записка уже существует в контейнере с id: $id")
                notes.add(elem.copy(id = ++notesCount))
                println("Записка c id: $notesCount была добавлена в контейнер с id: $id")
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При добавлении комментария нужно указать id записки")
                val foundNoteIndex = notes.indexOfFirst { it.id == noteId }
                when (foundNoteIndex) {
                    -1 -> throw NoteNotExistException("Нет записки с таким id")
                    else -> {
                        if (notes[foundNoteIndex].isDeleted) throw NoteIsDeletedException("Записка с id: $noteId удалена")
                    }
                }
//                if (notes[foundNoteIndex].comments.contains(elem)) throw
                if (notes[foundNoteIndex].comments.any { it == elem }) throw
                            CommentAlreadyExistException("В записке с id: $foundNoteIndex уже есть такой комментарий")
                notes[foundNoteIndex].comments.add(elem.copy(id = ++commentsCount))
                println("В записку с id: $foundNoteIndex добавлен комментарий с id: $commentsCount")
            }
        }
    }
}