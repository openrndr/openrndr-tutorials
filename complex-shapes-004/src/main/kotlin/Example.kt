import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

/**
 * This is a basic example that shows how to draw complex shapes using
 * Circle.contour
 * Contour.map()
 * Segment.copy()
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = null
        drawer.stroke = ColorRGBa.WHITE
        drawer.strokeWeight = 4.0

        // -- draw a circular contour with distorted control points
        drawer.contour(
                Circle(Vector2(width/2.0, height/2.0), 100.0).contour.map {
                    it.copy(control = it.control.map { it + Vector2((Math.random()-0.5)*40.0, (Math.random()-0.5)*40.0) }.toTypedArray())
                }
        )
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}