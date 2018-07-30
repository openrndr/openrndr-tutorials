import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extensions.Debug3D
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.math.map
import org.openrndr.shape.Rectangle

/**
 * This is an example that shows how to use clipping rectangles
 */
class Example: Program() {

    lateinit var points: ArrayList<Vector2>
    lateinit var camera: Debug3D

    override fun setup() {
        super.setup()

        points = ArrayList()
        camera = Debug3D(Vector3(0.0, 100.0, 200.0), Vector3(0.0, 0.0, 0.0))

        for(p in 0..10000) {
            points.add(Vector2(
                    -50+Math.random()*100,
                    -50+Math.random()*100
            ))
        }

        extend(camera)

        keyboard.keyDown.listen {
            if (it.name == "r") {
                camera.orbitalCamera.panTo(
                        Vector3(0.0, 0.0, 0.0)
                )
                camera.orbitalCamera.dollyTo(200.0)
                camera.orbitalCamera.rotateTo(Vector3(0.0, 100.0, 200.0))
            }
        }

    }

    override fun draw() {

        drawer.background(ColorRGBa.WHITE)
        val circleRadius = map(200.0, 0.0, 1.0 , 0.1, camera.orbitalCamera.spherical.radius)

        drawer.isolated {
            fill = ColorRGBa(0.0, 0.0, 0.0, 0.5)
            stroke = null
            drawer.rotate(Vector3.UNIT_X, 90.0)
            circles(points, circleRadius)
        }
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}