import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.isolated
import org.openrndr.draw.loadImage
import org.openrndr.draw.tint
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle

/**
 * This is a basic box2d example, for more information go to https://github.com/libgdx/libgdx/wiki/Box2d
 */
class Constants {
    companion object {
        var TIME_STEP = 1/60f
        var VELOCITY_ITERATIONS = 20
        var POSITION_ITERATIONS = 20
    }
}

class bodyRectangle(var body:Body, var width: Double, var height: Double)
class bodyCircle(var body:Body, var width: Double, var height: Double, var radius : Double)

fun main() = application {
    configure {
        width = 1000
        height = 1000
    }

    // Create
    lateinit var world : World
    var accumulator = 0f

    fun doPhysicsStep(deltaTime: Float) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        val frameTime = Math.min(deltaTime, 0.25f)
        accumulator += frameTime
        while (accumulator >= Constants.TIME_STEP) {
            world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS)
            accumulator -= Constants.TIME_STEP
        }
    }

    program {

        // Set
        world = World(Vector2(0.0f, 0.98f), false)

        val circles = arrayListOf<bodyCircle>()
        val rectangles = arrayListOf<bodyRectangle>()

        // Create rectangle
        drawer.isolated {

            val bodyDef = BodyDef()

            bodyDef.position.set(500.0f, 900.0f)
            bodyDef.type = BodyDef.BodyType.KinematicBody

            val body = world.createBody(bodyDef)

            val w = 600.0f
            val h = 100.0f

            val shape = PolygonShape()
            shape.setAsBox((w / 2.0).toFloat(), (h / 2.0).toFloat())
            val fixtureDef = FixtureDef()

            fixtureDef.shape = shape
            fixtureDef.density = 0.1f
            fixtureDef.friction = 0.4f
            fixtureDef.restitution = 0.01f

            body.createFixture(fixtureDef)
            rectangles.add(bodyRectangle(body, w.toDouble(), h.toDouble()))

        }

        // Create circles
        drawer.isolated {
            for (a in 0..100) {

                val bodyDef = BodyDef()

                val x = width * Math.random()
                val y = -height * Math.random()

                bodyDef.position.set(x.toFloat(), y.toFloat())
                bodyDef.type = BodyDef.BodyType.DynamicBody

                val body = world.createBody(bodyDef)

                val fixtureDef = FixtureDef()
                fixtureDef.density = 0.1f
                fixtureDef.friction = 0.4f
                fixtureDef.restitution = 0.01f
                fixtureDef.shape = CircleShape()

                val r: Double = (Math.random() * 50.0) + 10.0

                fixtureDef.shape.radius = r.toFloat()
                body.createFixture(fixtureDef)
                body.setLinearVelocity(0.0f, (Math.random() * 10.0f + 10.0f).toFloat())

                circles.add(bodyCircle(body, 1.0, 1.0, radius = r))
            }
        }

        extend {

            drawer.stroke = ColorRGBa.WHITE
            drawer.fill = ColorRGBa.GRAY.opacify(0.25)

            // draw floor
            drawer.rectangles(
                    rectangles.map {
                        Rectangle(it.body.position.x.toDouble() - it.width / 2, it.body.position.y.toDouble() - it.height / 2, it.width, it.height)
                    }
            )

            // drawer particles
            drawer.circles(
                    circles.map {
                        Circle(org.openrndr.math.Vector2(it.body.position.x.toDouble(), it.body.position.y.toDouble()), it.radius)
                    }
            )

            // update
            doPhysicsStep(seconds.toFloat())

        }
    }
}