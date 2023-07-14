package config

abstract class Configuration {
    abstract val GRID_WIDTH:Int
    abstract val GRID_HEIGHT:Int
    abstract val TILE_WIDTH:Int
    abstract val FRAME_STEP:Int

    abstract val TIME_SPEED:Int
    abstract val TIME_SLOW_SPEED:Int
    abstract val TIME_TEXT_BLINK_SPEED:Int

    abstract val FRAME_SKIP:Int
}