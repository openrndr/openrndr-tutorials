import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import org.openrndr.shape.LineSegment
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to draw complex shapes constructed from primitives
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = null
        drawer.stroke = ColorRGBa.WHITE
        drawer.strokeWeight = 4.0

        // -- draw a circular contour with a cut
        drawer.contour(Circle(Vector2(110.0, 110.0), 100.0).contour.sub(0.0, Math.cos(seconds)*0.5+0.5))

        // -- draw a rectangular contour with a cut
        drawer.contour(Rectangle(Vector2(210.0, 210.0), 100.0, 100.0).contour.sub(0.0, Math.cos(seconds)*0.5+0.5))

        // -- draw a line segment with a cut
        drawer.contour(LineSegment(Vector2(300.0, 100.00), Vector2(300.0, 200.0)).contour.sub(0.0, Math.cos(seconds)*0.5+0.5))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}