import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadImage
import org.openrndr.draw.tint

/**
 * This is a basic example that shows how to load and draw images and apply tinting
 */
class Image002 : Program() {
    override fun setup() {
        val image = loadImage("file:data/images/test_pattern.png")
        extend {
            drawer.drawStyle.colorMatrix = tint(ColorRGBa(1.0, 0.0, 0.0))
            drawer.image(image, 0.0, 0.0)
            drawer.drawStyle.colorMatrix = tint(ColorRGBa(0.0, 1.0, 0.0))
            drawer.image(image, image.width * 1.0, 0.0)
            drawer.drawStyle.colorMatrix = tint(ColorRGBa(0.0, 0.0, 1.0))
            drawer.image(image, image.width * 2.0, 0.0)
        }
    }
}

fun main() = Application.run(Image002(), Configuration())
