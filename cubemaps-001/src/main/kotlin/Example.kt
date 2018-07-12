import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.*
import org.openrndr.math.Vector3

class Example:Program() {

    lateinit var cubemap:Cubemap
    lateinit var cube:VertexBuffer
    override fun setup() {
        cubemap = Cubemap.fromUrl("file:data/textures/garage.dds")

        cube = vertexBuffer(
                vertexFormat {
                    position(3)
                    normal(3)
                    textureCoordinate(3)
                }, 6* 3 * 2
        )
        cube.put {
            val p000 = Vector3(-1.0, -1.0, -1.0)
            val p001 = Vector3(-1.0, -1.0, 1.0)
            val p010 = Vector3(-1.0, 1.0, -1.0)
            val p011 = Vector3(-1.0, 1.0, 1.0)

            val p100 = Vector3(1.0, -1.0, -1.0)
            val p101 = Vector3(1.0, -1.0, 1.0)
            val p110 = Vector3(1.0, 1.0, -1.0)
            val p111 = Vector3(1.0, 1.0, 1.0)

            val npx = Vector3(1.0, 0.0, 0.0)
            val nnx = Vector3(-1.0, 0.0, 0.0)
            val npy = Vector3(0.0, 1.0, 0.0)
            val nny = Vector3(0.0, -1.0, 0.0)

            val npz = Vector3(0.0, 0.0, 1.0)
            val nnz = Vector3(0.0, 0.0, -1.0)

            // -- positive x
            write(p100); write(npx); write(p100)
            write(p101); write(npx); write(p101)
            write(p111); write(npx); write(p111)

            write(p111); write(npx); write(p111)
            write(p110); write(npx); write(p110)
            write(p100); write(npx); write(p100)

            // -- negative x
            write(p000); write(nnx); write(p000)
            write(p001); write(nnx); write(p001)
            write(p011); write(nnx); write(p011)

            write(p011); write(nnx); write(p011)
            write(p010); write(nnx); write(p010)
            write(p000); write(nnx); write(p000)

            // -- positive y
            write(p010); write(npy); write(p010)
            write(p011); write(npy); write(p011)
            write(p111); write(npy); write(p111)

            write(p111); write(npy); write(p111)
            write(p110); write(npy); write(p110)
            write(p010); write(npy); write(p010)

            // -- negative y
            write(p000); write(nny); write(p000)
            write(p001); write(nny); write(p001)
            write(p101); write(nny); write(p101)

            write(p101); write(nny); write(p101)
            write(p100); write(nny); write(p100)
            write(p000); write(nny); write(p000)

            // -- positive z
            write(p001); write(npz); write(p001)
            write(p011); write(npz); write(p011)
            write(p111); write(npz); write(p111)

            write(p111); write(npz); write(p111)
            write(p101); write(npz); write(p101)
            write(p001); write(npz); write(p001)

            // -- negative z
            write(p000); write(nnz); write(p000)
            write(p010); write(nnz); write(p010)
            write(p110); write(nnz); write(p110)

            write(p110); write(nnz); write(p110)
            write(p100); write(nnz); write(p100)
            write(p000); write(nnz); write(p000)
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.GRAY)
        drawer.perspective(90.0, width*1.0/height, 0.1, 100.0)

        drawer.depthWrite = true
        drawer.depthTestPass = DepthTestPass.LESS

        drawer.lookAt(Vector3(0.0, 0.0, .5),Vector3(0.0, 0.0, 0.0))
        drawer.rotate(Vector3.UNIT_X, seconds*10.0)

        cubemap.filter(MinifyingFilter.NEAREST_MIPMAP_LINEAR, MagnifyingFilter.LINEAR)
        val shadeStyle = shadeStyle {
            fragmentTransform = """
                    x_fill = texture(p_cubemap, va_texCoord0);
                    """
            parameter("cubemap", cubemap)
        }
        drawer.shadeStyle = shadeStyle
        drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
    }
}

fun main(args: Array<String>) {
    application(Example(), configuration {  })
}