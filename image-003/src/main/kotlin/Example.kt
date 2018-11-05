import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadImage
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to draw many image rectangles at once
 */
class Image003 : Program() {
    override fun setup() {
        val image = loadImage("file:data/images/test_pattern.png")
        extend {
            drawer.background(ColorRGBa.BLACK)
            // -- note that a list of Pair<Rectangle, Rectangle> could have been produced here with ordinary for loops
            // -- instead we show a terse variant using flatmap/map
            drawer.image(image, (0..10).flatMap { y ->
                (0..10).map { x ->
                    val source = Rectangle(
                            (mouse.position.x / width) * (image.width - 64.0),
                            (mouse.position.y / height) * (image.height - 64.0), 64.0, 64.0)
                    val target = Rectangle(x * 64.0, y * 64.0, 64.0, 64.0)
                    source to target
                }
            })
        }
    }
}

fun main() = Application.run(Image003(), Configuration())