import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.draw.loadImage

/**
 * This is a basic example that shows how to load and draw images
 */
class Example : Program() {
    override fun setup() {
        val image = loadImage("file:data/images/test_pattern.png")
        extend {
            drawer.image(image)
        }
    }
}

fun main() = Application.run(Example(), Configuration())