import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap

/**
 * This is a basic example that shows how to display text using FontImageMap and Drawer.text
 **/
class Example: Program() {

    lateinit var font : FontImageMap

    override fun setup() {
        font = FontImageMap.fromUrl("data/fonts/Roboto-Medium.ttf", 16.0)
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fontMap = font
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = null
        drawer.text("HELLO WORLD", 100.0, 100.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}