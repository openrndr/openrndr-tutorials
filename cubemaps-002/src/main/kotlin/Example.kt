import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.*
import org.openrndr.math.Vector3
import org.openrndr.math.transforms.transform

class Example : Program() {

    lateinit var cubemap: Cubemap
    lateinit var irradiance: Cubemap
    lateinit var cube: VertexBuffer
    lateinit var texture: ColorBuffer
    lateinit var normals: ColorBuffer

    override fun setup() {
        cubemap = Cubemap.fromUrl("file:data/garage.dds")
        irradiance = Cubemap.fromUrl("file:data/garage_iem.dds")
        texture = ColorBuffer.fromFile("data/snake.png")
        normals = ColorBuffer.fromFile("data/snake-normal.png")

        cube = vertexBuffer(
                vertexFormat {
                    position(3)
                    normal(3)
                    attribute("tangent", 3, VertexElementType.FLOAT32)
                    attribute("binormal", 3, VertexElementType.FLOAT32)
                    textureCoordinate(3)
                }, 6 * 3 * 2
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
            write(p100); write(npx); write(npy); write(npz); write(p100)
            write(p101); write(npx); write(npy); write(npz); write(p101)
            write(p111); write(npx); write(npy); write(npz); write(p111)

            write(p111); write(npx); write(npy); write(npz); write(p111)
            write(p110); write(npx); write(npy); write(npz); write(p110)
            write(p100); write(npx); write(npy); write(npz); write(p100)

            // -- negative x
            write(p000); write(nnx); write(nny); write(nnz); write(p000)
            write(p001); write(nnx); write(nny); write(nnz); write(p001)
            write(p011); write(nnx); write(nny); write(nnz); write(p011)

            write(p011); write(nnx); write(nny); write(nnz); write(p011)
            write(p010); write(nnx); write(nny); write(nnz); write(p010)
            write(p000); write(nnx); write(nny); write(nnz); write(p000)

            // -- positive y
            write(p010); write(npy); write(npx); write(npz); write(p010)
            write(p011); write(npy); write(npx); write(npz); write(p011)
            write(p111); write(npy); write(npx); write(npz); write(p111)

            write(p111); write(npy); write(npx); write(npz); write(p111)
            write(p110); write(npy); write(npx); write(npz); write(p110)
            write(p010); write(npy); write(npx); write(npz); write(p010)

            // -- negative y
            write(p000); write(nny); write(nnx); write(nnz); write(p000)
            write(p001); write(nny); write(nnx); write(nnz); write(p001)
            write(p101); write(nny); write(nnx); write(nnz); write(p101)

            write(p101); write(nny); write(nnx); write(nnz); write(p101)
            write(p100); write(nny); write(nnx); write(nnz); write(p100)
            write(p000); write(nny); write(nnx); write(nnz); write(p000)

            // -- positive z
            write(p001); write(npz); write(npx); write(npy); write(p001)
            write(p011); write(npz); write(npx); write(npy); write(p011)
            write(p111); write(npz); write(npx); write(npy); write(p111)

            write(p111); write(npz); write(npx); write(npy); write(p111)
            write(p101); write(npz); write(npx); write(npy); write(p101)
            write(p001); write(npz); write(npx); write(npy); write(p001)

            // -- negative z
            write(p000); write(nnz); write(nnx); write(nny); write(p000)
            write(p010); write(nnz); write(nnx); write(nny); write(p010)
            write(p110); write(nnz); write(nnx); write(nny); write(p110)

            write(p110); write(nnz); write(nnx); write(nny); write(p110)
            write(p100); write(nnz); write(nnx); write(nny); write(p100)
            write(p000); write(nnz); write(nnx); write(nny); write(p000)
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.GRAY)
        drawer.perspective(45.0, width * 1.0 / height, 0.1, 100.0)

        drawer.depthWrite = true
        drawer.depthTestPass = DepthTestPass.LESS

        drawer.lookAt(Vector3(0.0, 1.0, 1.0), Vector3(0.0, 0.0, 0.0))

        cubemap.filter(MinifyingFilter.NEAREST_MIPMAP_LINEAR, MagnifyingFilter.LINEAR)

        val shadeStyle = shadeStyle {
            fragmentTransform = """
                    x_fill = texture(p_cubemap, va_texCoord0);
                    """
            parameter("cubemap", cubemap)
        }
        drawer.shadeStyle = shadeStyle
        drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)

        val shadeStyle2 = shadeStyle {

            fragmentPreamble = """
                // -- cube to planar
                vec2 c2p(vec3 c) {
                    vec3 ac = abs(c);
                    vec2 r = c.xy;
                    if (ac.x > ac.y && ac.x > ac.z) {
                        r = c.yz;
                    }
                    if (ac.y > ac.x && ac.y > ac.z) {
                        r = c.xz;
                    }
                    return r;
                }
                """

            fragmentTransform = """
                    vec4 baseColor = pow(texture(p_texture, (c2p(va_texCoord0)+vec2(1.0))/2.0),vec4(2.2));
                    vec3 normal = (texture(p_normals, (c2p(va_texCoord0)+vec2(1.0))/2.0).xyz-vec3(0.5))*2.0;

                    // -- construct local tbn matrix
                    mat3 tbn = mat3(va_tangent, va_binormal, va_normal);

                    // -- lookup irradiance
                    x_fill = pow(texture(p_irradiance, mat3(u_modelNormalMatrix) * tbn * normal),vec4(2.2)) * baseColor;

                    // -- lookup specular reflection
                    vec3 nv = normalize(v_viewPosition);
                    vec3 viewNormal = normalize(mat3(u_viewNormalMatrix * u_modelNormalMatrix) * tbn * normal);
                    float s = max(0.0,-dot(nv,viewNormal));
                    s = pow(s,10.0);
                    vec3 specularNormal = (inverse(u_viewMatrix) * vec4(reflect(nv, viewNormal ),0.0)).xyz;

                    x_fill.rgb += pow(texture(p_cubemap, specularNormal).rgb*1.0*s, vec3(2.2));
                    x_fill.rgb = pow(x_fill.rgb, vec3(1.0/2.2));
                    """
            parameter("irradiance", irradiance)
            parameter("cubemap", cubemap)
            parameter("texture", texture)
            parameter("normals", normals)
        }

        drawer.shadeStyle = shadeStyle2

        drawer.model = transform {
            rotate(Vector3.UNIT_Y, seconds * 20)
            rotate(Vector3.UNIT_X, seconds * 12)
            scale(0.3)
        }
        drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
    }
}

fun main(args: Array<String>) {
    application(Example(), configuration {

        width = 1280
        height = 720
    })
}