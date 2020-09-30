fun main() {
    val container = Container(1)
    val note1 = Note("Title1", "text1")
    val note2 = Note("Title1", "text1")
    container.add(note1)
//    container.add(note2)
    println("note1 == note2: ${note1 == note2}")
    println("Notes ids: ${note1.id}, ${note2.id}")

}