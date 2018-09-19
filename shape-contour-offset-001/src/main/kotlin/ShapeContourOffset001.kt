import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import org.openrndr.shape.SegmentJoin

class ShapeContourOffset001: Program() {

    override fun draw() {
        drawer.fill = null
        drawer.stroke = ColorRGBa.PINK

        val contour = Rectangle(Vector2(width/2.0-50.0, height/2.0-50.0), 100.0, 100.0).contour
        val contourWithOffset = contour.offset(mouse.position.y, SegmentJoin.ROUND)

        drawer.contour(contourWithOffset)
        drawer.stroke = drawer.stroke?.opacify(0.5)
        drawer.contour(contour)
    }

}

fun main(args: Array<String>) {
    application(ShapeContourOffset001(), configuration {

    })

}