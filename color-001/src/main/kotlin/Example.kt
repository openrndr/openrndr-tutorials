import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorHSVa

/**
 * This is a basic example that shows color basics, specifically the conversion from HSV to RGB color space
 */
class Example: Program() {
    override fun draw() {
        val hsv = ColorHSVa((mouse.position.x / width) * 360.0, mouse.position.y/height, 1.0)
        drawer.background(hsv.toRGBa())
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}