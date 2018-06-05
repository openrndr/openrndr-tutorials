import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer

/**
 * This is a basic example that shows how to display text using FontImageMap and Writer.
 *
 * Be sure to set the working directory in the run configuration to $MODULE_DIR$
 */
class Example: Program() {

    lateinit var font : FontImageMap

    override fun setup() {
        font = FontImageMap.fromUrl("file:text-002/data/fonts/Roboto-Medium.ttf", 16.0)
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fontMap = font
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = null

        Writer(drawer).apply {
            box = Rectangle(Vector2(100.0, 100.0), width-200.0, height-200.0)
            for (i in 0 until 10) {
                text("HELLO WORLD")
                newLine()
            }
        }
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}