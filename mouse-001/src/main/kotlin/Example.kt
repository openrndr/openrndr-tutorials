import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa

/**
 * This is a basic example that shows how to use the mouse position
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.PINK
        drawer.circle(mouse.position, 20.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}