import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector2
import org.openrndr.shape.contour

/**
 * This is a basic example that shows how to use shade styles

 */
class Example : Program() {

    lateinit var style: ShadeStyle
    lateinit var image: ColorBuffer
    override fun setup() {
        // -- define a simple that style produces a circular gradient
        style = shadeStyle {
            fragmentTransform = """
                x_fill.rgb *= 1.0-length(c_boundsPosition.xy - p_position);
            """
        }

        image = ColorBuffer.fromUrl("file:data/images/test_pattern.png")
    }

    override fun draw() {
        drawer.background(ColorRGBa.GRAY.shade(0.5))

        // -- apply the style, it will be in effect for all following draw calls
        style.parameter("position", mouse.position / window.size)
        drawer.shadeStyle = style

        drawer.fill = ColorRGBa.PINK
        drawer.rectangle(30.0, 30.0, 100.0, 100.0)
        drawer.rectangle(140.0, 30.0, 100.0, 200.0)

        drawer.circle(Vector2(330.0, 330.0), 50.0)
        drawer.circle(Vector2(530.0, 230.0), 150.0)

        drawer.contour(contour {
            moveTo(Vector2(320.0, 20.0))
            lineTo(Vector2(400.0, 20.0))
            lineTo(Vector2(400.0, 100.0))
            lineTo(Vector2(320.0, 120.0))
            lineTo(Vector2(320.0, 20.0))
            close()
        })

        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 132.0)
        drawer.text("SHADE STYLED TEXT!", 100.0, 500.0)

        drawer.image(image)
    }
}

fun main(args: Array<String>) {
    application(
            Example(),
            configuration {
                width =  1280
                height = 720
            }
    )
}