interface Crudable {

    fun <T> add(elem: T, nid: Int? = null)
}