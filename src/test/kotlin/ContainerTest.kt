import org.junit.Test
import org.junit.Assert.*
import java.lang.IllegalStateException


class ContainerTest {

    @Test(expected = IllegalStateException::class)
    fun addingContainer_inContainer() {
        val container = Container(1)
        val container2 = Container(2)
        container.add(container2)
    }

    @Test
    fun addingNote_Success() {
        val container = Container(1)
        val note = Note("title", "text")
        container.add(note)
        assertEquals(1, container.notes.size)
    }

    @Test(expected = NoteAlreadyExistException::class)
    fun addingNote_NoteAlreadyExist() {
        val container = Container(1)
        val note = Note("title", "text")
        container.add(note)
        container.add(note)
    }

//    @Test(expected = NoteAlreadyExistException::class)
//    fun addingNote_NoteAlreadyExist_inDeleted() {
//
//    }
}