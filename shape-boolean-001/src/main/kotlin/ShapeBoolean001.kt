import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.math.Vector2
import org.openrndr.shape.*

class ShapeBoolean001 : Program() {
    override fun draw() {
        val a = Circle(Vector2(width / 2.0, height / 2.0), 100.0).contour
        val b = Circle(mouse.position, 70.0).contour
        val d = Circle(mouse.position.copy(x = width-mouse.position.x), 50.0).contour
        val e = Circle(mouse.position.copy(x = width-mouse.position.x+40.0), 50.0).contour
        drawer.fill = ColorRGBa.YELLOW
        drawer.stroke = ColorRGBa.PINK
        drawer.shapes(compound {
            union {
                difference {
                    shape(a)
                    shape(b)
                }
                difference {
                    shape(d)
                    shape(e)
                }
            }
        })
    }
}

fun main(args: Array<String>) {
    application(ShapeBoolean001(), configuration {  })

}