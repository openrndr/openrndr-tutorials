import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector2

/**
 * This is a basic example that shows how to draw a single line segment and change the line drawing quality
 */
class Example : Program() {

    var quality = DrawQuality.PERFORMANCE
    override fun setup() {
        mouse.clicked.listen {
            quality = when (quality) {
                DrawQuality.PERFORMANCE -> DrawQuality.QUALITY
                DrawQuality.QUALITY -> DrawQuality.PERFORMANCE
            }
        }
    }

    override fun draw() {
        drawer.fill = ColorRGBa.WHITE // work around a bug in 0.3.20, is fixed in 0.3.21 and up
        drawer.stroke = ColorRGBa.WHITE
        drawer.drawStyle.quality = quality
        drawer.lineSegment(Vector2(0.0, 0.0), Vector2(width * 1.0, height * 1.0))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}