import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.transforms.transform
import org.openrndr.shape.Triangulator
import org.openrndr.svg.loadSVG
import java.io.File

/**
 * This is a basic example that shows how to draw instances of fully custom geometry using VertexBuffers,
 * ShadeStyles and Drawer.vertexBufferInstances()
 */
class Example : Program() {

    lateinit var geometry: VertexBuffer
    lateinit var transforms: VertexBuffer
    override fun setup() {


        val c = loadSVG(File("custom-rendering-004/data/shape.svg").readText())
        val shape = c.findShapes()[0].shape

        val points = Triangulator().triangulate(shape.outline)

        // -- create the vertex buffer
        geometry = vertexBuffer(
                vertexFormat {
                    position(3)
                }, points.size)

        // -- fill the vertex buffer with vertices
        geometry.put {
            points.forEach {
                write(it.vector3(z=0.0))
            }
        }

        // -- create the transform buffer
        transforms = vertexBuffer(
                vertexFormat {
                    attribute("transform", 16, VertexElementType.FLOAT32)
                    color(4)
                }, 1000)

        // -- fill the transform buffer
        transforms.put {
            for (i in 0 until 1000) {
                write(transform {
                    translate((i%10)*100.0,(i/10)*100.0,0.0)

                })
            }
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = ColorRGBa.WHITE


        // -- fill the transform buffer
        transforms.put {
            for (i in 0 until 1000) {
                write(transform {
                    translate((i%20)*100.0,(i/20)*100.0,0.0)
                    translate(50.0, 50.0)
                    rotate(Vector3.UNIT_Z, seconds*10.0+i*10.0)
                    translate(-50.0, -50.0)
                })
                write(ColorRGBa.RED.shade(Math.cos(seconds+i)*0.5+0.5))
            }
        }

        drawer.shadeStyle = shadeStyle {
            vertexTransform = """
                x_viewMatrix = x_viewMatrix * i_transform;
            """
            fragmentTransform = """
                x_fill = vi_color;
                """
        }

        drawer.vertexBufferInstances(listOf(geometry), listOf(transforms), DrawPrimitive.TRIANGLES, transforms.vertexCount)
    }
}

fun main(args: Array<String>) {
    application(
            Example(),
            configuration {
                width =  1920
                height = 1080
            }
    )
}