import org.junit.Test
import org.junit.Assert.*


class NotesBoxTest {

    @Test(expected = IllegalStateException::class)
    fun add_ContainerInContainer() {
        val container = NotesBox()
        val container2 = NotesBox()
        container.add(container2)
    }

    @Test
    fun add_Note_Success() {
        val container = NotesBox()
        val note = Note("title", "text")
        container.add(note)
        assertEquals(1, container.notesBox.size)
    }

    @Test(expected = ElementAlreadyExistException::class)
    fun add_Note_NoteAlreadyExist() {
        val container = NotesBox()
        val note = Note("title", "text")
        container.add(note)
        container.add(note)
    }

//    @Test(expected = ElementAlreadyExistException::class)
//    fun add_Note_NoteAlreadyExist_inDeleted() {
//
//    }
}
