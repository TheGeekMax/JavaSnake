package player

import KeyboardManager
import Snake
import java.awt.event.KeyEvent
import java.util.*

class SimpleAi(instance:Snake):Player(instance) {
    private val queue: Queue<Int> = LinkedList<Int>()
    private var last = 1
    private var cooldown = 0
    override fun play():Int{
        //on test si on est au bord
        if(cooldown -- < 0) {
            val headP = instance.getHeadPos()
            if (headP.x == 0) {
                cooldown = 2
                queue.add(4)
                queue.add(1)
            } else if (headP.x == instance.getWidth() - 1) {
                cooldown = 2
                queue.add(4)
                queue.add(3)
            }
        }

        //on retourne la valeur adequat
        if(queue.size == 0){
            return last
        }
        val new = queue.remove()
        last = new
        return new
    }

    override fun keyPressed(e: KeyEvent){}
}