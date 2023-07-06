data class Vector2Int(var x: Int, var y: Int) {
    fun moduleSqred(): Int {
        return x * x + y * y
    }
}