package player

import KeyboardManager
import Snake
import java.awt.event.KeyEvent

class Human(instance:Snake):Player(instance) {
    private val keyboard: KeyboardManager = KeyboardManager(KeyEvent.VK_Z, KeyEvent.VK_Q, KeyEvent.VK_S, KeyEvent.VK_D)
    override fun play():Int = keyboard.getDirrection()

    override fun keyPressed(e: KeyEvent){
        keyboard.keyPressed(e)
    }
}