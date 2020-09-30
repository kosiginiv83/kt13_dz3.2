import org.junit.Test
import org.junit.Assert.*


class ContainerTest {

    @Test(expected = IllegalStateException::class)
    fun add_ContainerInContainer() {
        val container = Container(1)
        val container2 = Container(2)
        container.add(container2)
    }

    @Test
    fun add_Note_Success() {
        val container = Container(1)
        val note = Note("title", "text")
        container.add(note)
        assertEquals(1, container.notes.size)
    }

    @Test(expected = ElementAlreadyExistException::class)
    fun add_Note_NoteAlreadyExist() {
        val container = Container(1)
        val note = Note("title", "text")
        container.add(note)
        container.add(note)
    }

//    @Test(expected = ElementAlreadyExistException::class)
//    fun add_Note_NoteAlreadyExist_inDeleted() {
//
//    }
}