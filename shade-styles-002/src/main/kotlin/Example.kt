import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to use shade styles with rectangles and circles
 */
class Example : Program() {

    lateinit var style: ShadeStyle
    lateinit var data: VertexBuffer
    lateinit var image: ColorBuffer
    override fun setup() {

        image = ColorBuffer.fromUrl("file:data/images/test_pattern.png")

        // -- specify a vertexbuffer that will be used a data buffer
        // -- you can add more attributes here
        data = vertexBuffer( vertexFormat  {
            attribute("color", 4, VertexElementType.FLOAT32) // <- note the name of the attribute
        }, 50) // <- you can safely use much high numbers here

        // -- write random colors in this data buffer
        data.put {
            for (i in 0 until data.vertexCount) {
                write(ColorRGBa(Math.random(), Math.random(), Math.random(), 1.0))
            }
        }

        // -- define a simple style assigns the color from the data buffer to x_fill
        style = shadeStyle {
            fragmentTransform = """
                x_fill *= vi_color;
            """
            // -- this links up data with 'vi_color' in the fragmentTransform
            attributes(data)
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.GRAY.shade(0.5))
        drawer.fill = ColorRGBa.WHITE

        // -- apply the style, it will be in effect for all following draw calls
        drawer.shadeStyle = style

        drawer.rectangles((0 until data.vertexCount).map {
            Rectangle(40.0+ it * 20.0, 20.0, 15.0,  100.0)
        })

        // -- we use the same colors for the circles
        drawer.circles((0 until data.vertexCount).map {
            Circle(Vector2(40.0+it*20.0, 200.0), 10.0)
        })

        drawer.image(image, (0 until data.vertexCount).map {
            Rectangle(0.0, 0.0, 15.0, 100.0) to Rectangle(40.0+ it * 20.0, 320.0, 15.0,  100.0)
        })

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