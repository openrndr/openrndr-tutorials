import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.PresentationMode
import org.openrndr.Program
import org.openrndr.color.ColorRGBa

/**
 * This is a basic example that demonstrates the principles of manual presentation
 */
class Example: Program() {
    override fun setup() {
        window.presentationMode = PresentationMode.MANUAL
        mouse.clicked.listen {
            window.requestDraw()
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK.shade(Math.cos(seconds)))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}