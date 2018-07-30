import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer

/**
 * This is a basic example that shows how to load and draw images
 */
class Example: Program() {

    lateinit var image: ColorBuffer
    override fun setup() {
        image = ColorBuffer.fromUrl("file:data/images/test_pattern.png")
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.image(image)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}