import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector3

/**
 * This is a basic example that shows how to draw fully custom geometry using a VertexBuffer
 * additionally the geometry is shaded by using a ShadeStyle.
 */
class Example: Program() {

    lateinit var geometry: VertexBuffer
    override fun setup() {
        // -- create the vertex buffer
        geometry = vertexBuffer(
                vertexFormat {
                    position(3)
                }, 6)

        // -- fill the vertex buffer with vertices
        geometry.put {
            val a = Vector3(10.0, 10.0, 0.0)
            val b = Vector3(width-10.0, 10.0, 0.0)
            val c = Vector3(width-10.0, height-10.0, 0.0)
            val d = Vector3(10.0, height-10.0, 0.0)
            write(a, b, c, c, d, a)
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.fill = ColorRGBa.WHITE
        drawer.shadeStyle = shadeStyle {
            fragmentTransform = """
                x_fill.rgb = vec3(cos(view.position.x*0.1)*0.5+0.5, 0.0, 0.0);
                """
        }
        drawer.vertexBuffer(geometry, DrawPrimitive.TRIANGLES)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}