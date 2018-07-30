import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.shadeStyle
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer

/**
 * This is a basic example that shows how to display text using FontImageMap and shade styles
 *
 */

class Example: Program() {

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 128.0)
        drawer.fill = ColorRGBa.PINK

        drawer.shadeStyle = shadeStyle {
            fragmentTransform = """
                    float stop = smoothstep(-0.01, 0.01, cos(c_element + p_time + c_boundsPosition.y )) * 0.5 + 0.5;
                    x_fill.a *= stop;
                """
            parameter("time", seconds * 1.0)
        }

        drawer.text("HELLO WORLD", width / 2.0 - 320.0, height / 2.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}