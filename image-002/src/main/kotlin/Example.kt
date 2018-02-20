import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.tint

/**
 * This is a basic example that shows how to load and draw images and apply tinting
 */
class Example: Program() {

    lateinit var image: ColorBuffer
    override fun setup() {
        image = ColorBuffer.fromUrl("file:data/test_pattern.png")
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.drawStyle.colorMatrix = tint(ColorRGBa(1.0, 0.0, 0.0))
        drawer.image(image, 0.0, 0.0)
        drawer.drawStyle.colorMatrix = tint(ColorRGBa(0.0, 1.0, 0.0))
        drawer.image(image, image.width*1.0, 0.0)
        drawer.drawStyle.colorMatrix = tint(ColorRGBa(0.0, 0.0, 1.0))
        drawer.image(image, image.width*2.0,0.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}