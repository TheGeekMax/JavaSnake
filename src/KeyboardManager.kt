import java.awt.event.KeyEvent
import java.util.*

class KeyboardManager (val keyUp:Int, val keyLeft:Int, val keyDown:Int, val keyRight:Int) {
    private val dirs: Queue<Int> = LinkedList<Int>()
    private var lastInputDirecton = 1;
    private var lastdirection = 1;

    fun getDirrection(): Int {
        if(dirs.size == 0){
            //c'est vide, on renvoie le dernier sortie
            return lastdirection
        }
        //c'est pas vide, on defile
        var temp:Int = dirs.remove()
        lastdirection = temp
        return temp
    }

    fun keyPressed(key: KeyEvent){
        if(lastInputDirecton == key.keyCode) {
            return
        }
        lastInputDirecton = key.keyCode

        when(key.keyCode){
            keyUp ->  dirs.add(4)
            keyLeft -> dirs.add(3)
            keyDown -> dirs.add(2)
            keyRight -> dirs.add(1)
        }
    }
}