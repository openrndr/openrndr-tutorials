import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2

/**
 * This is a basic example that shows some color basics
 */
class Example: Program() {

    val movingCircle = MovingCircle()

    override fun setup() {
        movingCircle.apply {
            animate(::x, width*1.0, 4000, Easing.CubicInOut)
            animate(::y, height*1.0, 4000, Easing.CubicInOut)
            animate(::radius, 20.0, 2000)
            complete()
            animate(::radius, 0.0, 2000)
        }
    }

    override fun draw() {
        movingCircle.updateAnimation()
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.PINK
        drawer.stroke = null
        drawer.circle(Vector2(movingCircle.x, movingCircle.y), movingCircle.radius)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}