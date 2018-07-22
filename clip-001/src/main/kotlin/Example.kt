import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

/**
 * This is an example that shows how to use clipping rectangles
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.PINK
        drawer.stroke = null
        drawer.drawStyle.clip = Rectangle(mouse.position - Vector2(200.0, 200.0), 400.0, 400.0)
        drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}