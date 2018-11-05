import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.transforms.transform
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to draw many rectangles at once and how to give per instance transforms
 */
class Example : Program() {
    lateinit var transforms: VertexBuffer
    private val rectangleCount = 100000

    override fun setup() {
        transforms = vertexBuffer(vertexFormat {
            attribute("transform", VertexElementType.MATRIX44_FLOAT32)
            attribute("color", VertexElementType.VECTOR4_FLOAT32)
        }, rectangleCount)
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.PINK
        drawer.stroke = ColorRGBa.WHITE.opacify(0.1)
        drawer.strokeWeight = 2.0

        transforms.put {
            for (i in 0 until rectangleCount) {
                write(
                        transform {
                            translate(Math.cos(i * 3.34234) * width, Math.sin(i * 1.43234) * height)
                            rotate(Vector3(0.0, 0.0, 1.0), seconds * 360.0 + i)
                            scale(10.0, 3.0, 1.0)
                        })
                write(ColorRGBa(Math.cos(i * 0.432), Math.cos(i * 0.4654), Math.cos(i * 0.543)))
            }
        }

        drawer.shadeStyle = shadeStyle {
            vertexTransform = """
                x_modelMatrix *= i_transform;
            """.trimIndent()
            fragmentTransform = """
                x_fill = vi_color;
            """.trimIndent()
            attributes(transforms)
        }
        drawer.rectangles(List(rectangleCount) { Rectangle(-0.5, -0.5, 1.0, 1.0) })
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 1280
        height = 720
    })
}