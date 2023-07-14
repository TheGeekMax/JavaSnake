package config

class AiConfig:Configuration() {
    override val GRID_WIDTH:Int = 22
    override val GRID_HEIGHT:Int = 22
    override val TILE_WIDTH:Int = 32
    override val FRAME_STEP:Int = 10

    override val TIME_SPEED:Int = 1
    override val TIME_SLOW_SPEED:Int = 1
    override val TIME_TEXT_BLINK_SPEED:Int = 1

    override val FRAME_SKIP:Int = 20
}