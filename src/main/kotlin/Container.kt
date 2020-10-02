class Container(id: Int): Crudable, Restorable {
    val id: Int = id
//    val notes: MutableList<Note> = mutableListOf()
    val notes = mutableListOf<Note>()
    private var notesCount = 0
    private var commentsCount = 0

    fun <T> findElement(element: T, list: MutableList<T>): T? = list.find { it == element }
    fun <T> findElementIndex(element: T, list: MutableList<T>): Int = list.indexOf(element)
    fun findElementIndex(element: Component, container: Component): Int {
        if (element is Comment && container is Note) {
            return container.comments.indexOf(element)
        }
        throw IllegalStateException("Недопустимая операция")
    }

    override fun <T> add(element: T, noteId: Int?) {
        when (element) {
            is Container -> throw IllegalStateException("Нельзя добавить контейнер в контейнер")
            is Note -> {
                when (val foundNoteIndex = notes.indexOf(element)) {
                    -1 -> {
                        notes.add(element.copy(id = ++notesCount))
                        println("Записка c id: $notesCount была добавлена в контейнер с id: $id")
                    }
                    else -> {
                        if (notes[foundNoteIndex].isDeleted) throw
                                ElementIsDeletedException("Идентичная записка находится в корзине контейнера с id: $id")
                        throw ElementAlreadyExistException("Идентичная записка уже существует в контейнере с id: $id")
                    }
                }
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При добавлении комментария нужно указать id записки")
                val foundNoteIndex = notes.indexOf(element)
                when (foundNoteIndex) {
                    -1 -> throw ElementNotFoundException("Нет записки с таким id")
                    else -> {
                        if (notes[foundNoteIndex].isDeleted) throw
                                ElementIsDeletedException("Записка с id: $foundNoteIndex удалена")
                    }
                }
                if (notes[foundNoteIndex].comments.contains(element)) throw
                        ElementAlreadyExistException("В записке с id: ${notes[foundNoteIndex].id} уже есть такой комментарий")
                notes[foundNoteIndex].comments.add(element.copy(id = ++commentsCount))
                println("В записку с id: ${notes[foundNoteIndex].id} добавлен комментарий с id: $commentsCount")
            }
        }
    }

    override fun <T> delete(element: T, noteId: Int?) {
        when (element) {
            is Container -> throw IllegalStateException("Нельзя удалить контейнер")
            is Note -> {
                when (val foundNoteIndex = notes.indexOf(element)) {
                    -1 -> throw
                            ElementNotFoundException("В контейнере с id: $id отсутствует записка с id: ${element.id}")
                    else -> {
                        if (notes[foundNoteIndex].isDeleted) throw
                                ElementIsDeletedException("Данная записка уже находится в корзине контейнера с id: $id")
                        notes[foundNoteIndex].isDeleted = true
                        println("Записка c id: ${notes[foundNoteIndex].id} была удалена в корзину контейнера с id: $id")
                    }
                }
            }
            is Comment -> {
                if (noteId == null) throw IllegalStateException("При удалении комментария нужно указать id записки")
                val foundNoteIndex = notes.indexOf(element)
            }
        }
    }
}