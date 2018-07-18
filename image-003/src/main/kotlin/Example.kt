import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to draw many image rectangles at once
 */
class Example : Program() {

    lateinit var image: ColorBuffer
    override fun setup() {
        image = ColorBuffer.fromUrl("file:image-003/data/test_pattern.png")
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        // -- note that a list of Pair<Rectangle, Rectangle> could have been produced here with ordinary for loops
        // -- instead we show a terse variant using flatmap/map
        drawer.image(image, (0..10).flatMap { y ->
            (0..10).map { x ->
                val source = Rectangle((mouse.position.x / width) * (image.width - 64.0), (mouse.position.y / height) * (image.height - 64.0), 64.0, 64.0)
                val target = Rectangle(x * 64.0, y * 64.0, 64.0, 64.0)
                source to target
            }
        })
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}