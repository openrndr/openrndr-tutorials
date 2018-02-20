import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa

/**
 * This is a basic example that shows how to use the mouse position and mouse button events
 */
class Example: Program() {
    var radius = 20.0

    override fun setup() {
        mouse.buttonDown.listen {
            radius = 100.0
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.PINK
        drawer.circle(mouse.position, radius)
        radius = 20.0
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}