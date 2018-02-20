import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2

/**
 * Simple program intended to run on the left side of the screen
 */
class Left:Program() {
    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.fill = ColorRGBa.BLACK

        for (i in 0 .. 10) {
            drawer.circle(Vector2(Math.cos(seconds+i)*width/2.0 + width/2.0,Math.sin(seconds+i)*height/2.0 + height/2.0 ), 30.0)
        }
    }
}

/**
 * Simple program intended to run on the right side of the screen
 */
class Right: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.RED)
        drawer.fill = ColorRGBa.BLACK
        for (i in 0 .. 10) {
            drawer.circle(Vector2(Math.cos(seconds+i)*width/2.0 + width/2.0,Math.sin(seconds*2.0+i)*height/2.0 + height/2.0 ), 30.0)
        }
    }
}

fun main(args: Array<String>) {
    Application.runAsync(Left(), configuration {
        position = IntVector2(0,100)
    })
    Application.runAsync(Right(), configuration {
        position = IntVector2(640*2, 100)
    })
}
