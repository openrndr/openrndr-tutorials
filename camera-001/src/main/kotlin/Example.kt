import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extensions.Debug3D
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3

/**
 * This is an example that shows how to use the Debug3D extension
 */
class Example: Program() {

    var points = mutableListOf<Vector2>()
    lateinit var camera: Debug3D

    override fun setup() {
        super.setup()

        camera = Debug3D(Vector3(0.0, 100.0, 200.0), Vector3(0.0, 0.0, 0.0))

        for(p in 0..10000) {
            points.add(Vector2(
                    -50+Math.random()*100,
                    -50+Math.random()*100
            ))
        }

        extend(camera)

        // -- optionally add a key listener to reset the camera on key press
        keyboard.keyDown.listen {
            if (it.name == "r") {
                camera.orbitalCamera.panTo(Vector3(0.0, 0.0, 0.0))
                camera.orbitalCamera.dollyTo(200.0)
                camera.orbitalCamera.rotateTo(Vector3(0.0, 100.0, 200.0))
            }
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.isolated {
            fill = ColorRGBa.BLACK.opacify(0.1)
            stroke = null
            drawer.rotate(Vector3.UNIT_X, 90.0)
        }
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}