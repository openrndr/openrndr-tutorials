import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import org.openrndr.shape.shape

/**
 * This is a basic example that shows how to draw complex shapes made using the shape {} and contour {} builders
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = null

        val s = shape {
            // -- primary contour defines the outline of the shape
            contour {
                moveTo(Vector2(10.0, 10.0))
                lineTo(cursor + Vector2(400.0, 0.0))
                lineTo(cursor + Vector2(0.0, 400.0))
                lineTo(cursor - Vector2(400.0, 0.0))
                lineTo(cursor - Vector2(0.0, 400.0))
                lineTo(anchor)
            }
            // -- secondary contours define holes
            contour {
                moveTo(Vector2(110.0, 110.0))
                lineTo(cursor + Vector2(200.0, 0.0))
                lineTo(cursor + Vector2(0.0, 200.0))
                lineTo(cursor - Vector2(200.0, 0.0))
                lineTo(cursor - Vector2(0.0, 200.0))
                lineTo(anchor)
            }
        }
        drawer.shape(s)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}