package player

import Snake
import java.awt.event.KeyEvent

abstract class Player(val instance:Snake){

    abstract fun play():Int //retourne la dirrection a avoir
    abstract fun keyPressed(e:KeyEvent) // lors de la pression d'une touche
}