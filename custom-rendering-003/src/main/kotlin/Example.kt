import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.transforms.transform

/**
 * This is a basic example that shows how to draw instances of fully custom geometry using VertexBuffers,
 * ShadeStyles and Drawer.vertexBufferInstances()
 */
class Example : Program() {

    lateinit var geometry: VertexBuffer
    lateinit var transforms: VertexBuffer
    override fun setup() {
        // -- create the vertex buffer
        geometry = vertexBuffer(
                vertexFormat {
                    position(3)
                }, 6)

        // -- fill the vertex buffer with vertices
        geometry.put {
            val a = Vector3(-10.0, -10.0, 0.0)
            val b = Vector3(10.0, -10.0, 0.0)
            val c = Vector3(10.0, 10.0, 0.0)
            val d = Vector3(-10.0, 10.0, 0.0)
            write(a, b, c, c, d, a)
        }

        // -- create the transform buffer
        transforms = vertexBuffer(
                vertexFormat {
                    attribute("transform", 16, VertexElementType.FLOAT32)
                }, 1000)

        // -- fill the transform buffer
        transforms.put {
            for (i in 0 until 1000) {
                write(transform {
                    translate(Math.random() * width, Math.random() * height)
                    rotate(Vector3.UNIT_Z, Math.random() * 360.0)
                })
            }
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = ColorRGBa.WHITE

        drawer.shadeStyle = shadeStyle {
            vertexTransform = """
                x_viewMatrix = x_viewMatrix * i_transform;
            """
        }

        drawer.vertexBufferInstances(listOf(geometry), listOf(transforms), DrawPrimitive.TRIANGLES, transforms.vertexCount)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}