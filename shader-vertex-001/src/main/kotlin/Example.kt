import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.*
import org.openrndr.extra.noise.valueLinear
import org.openrndr.extras.meshgenerators.wallPlaneMesh

/**
 * This programs demonstrates
 * - How to use transformation matrices in the vertex shader to deform
 * objects by displacing vertices.
 * - How to generate 2D noise using orx-noise
 * - How to create a plane mesh using orx-mesh-generators
 */
class Example : Program() {

    private var seed = 0;
    private lateinit var rectMesh: VertexBuffer
    lateinit var style: ShadeStyle

    override fun setup() {

        style = shadeStyle {
            vertexTransform = """
                vec2 center = u_viewDimensions.xy * 0.5;

                x_viewMatrix *= mat4( // translate xy + display center
                 1.0, 0.0, 0.0, 0.0,
                 0.0, 1.0, 0.0, 0.0,
                 0.0, 0.0, 1.0, 0.0,
                 center.x, center.y, 0.0, 1.0);

                float dist = distance(center,
                    (u_modelMatrix * vec4(a_position, 1.0)).xy);
                float angle = 0.7853981634 *
                    (1.0 - smoothstep(150.0, 350.0, dist)) * sin(p_time);

                x_viewMatrix *= mat4( // rotate 2D
                  cos(angle), sin(angle), 0.0, 0.0,
                 -sin(angle), cos(angle), 0.0, 0.0,
                 0.0, 0.0, 1.0, 0.0,
                 0.0, 0.0, 0.0, 1.0);

                x_viewMatrix *= mat4( // translate xy - display center
                 1.0, 0.0, 0.0, 0.0,
                 0.0, 1.0, 0.0, 0.0,
                 0.0, 0.0, 1.0, 0.0,
                 -center.x, -center.y, 0.0, 1.0);

            """.trimIndent()

            fragmentTransform = """
                float lum = 0.5 + 0.5 * cos(0.3 * length(
                    c_screenPosition.xy * 0.03 + va_position.xy * 0.05));

                x_fill.rgb += 0.1 - 0.2 * lum * lum;
            """.trimIndent()
        }

        mouse.buttonDown.listen {
            seed++
        }

        rectMesh = wallPlaneMesh(128.0, 72.0, 32, 18)
    }

    override fun draw() {
        drawer.background(ColorRGBa.GRAY.shade(0.5))
        drawer.stroke = null
        drawer.shadeStyle = style
        style.parameter("time", seconds)

        for (x in 0..9) {
            for (y in 0..9) {
                // valueLinear = 2D noise, from orx-noise
                val colorId = 100 + (10 * valueLinear(seed, x * 0.2, y * 0.1))
                        .toInt()

                drawer.fill = ColorRGBa.fromHex(listOf(0x5E9FA3, 0xDCD1B4,
                        0xFAB87F, 0xF87E7B, 0xB05574)[colorId % 5])

                // Option 1. Simple rect with 4 vertices
                //drawer.rectangle(x * 128.0, y * 72.0, 128.0, 72.0)

                // Option 2. Rectangle contour with many vertices.
                // Tesselation produces artifacts when deforming edges.
                //drawer.contour(contour {
                //    moveTo(Vector2(x * 128.0, y * 72.0))
                //    for (i in 0..32) lineTo(cursor + Vector2(0.0, 2.0))
                //    for (i in 0..61) lineTo(cursor + Vector2(2.0, 0.0))
                //    for (i in 0..32) lineTo(cursor + Vector2(0.0, -2.0))
                //    for (i in 0..61) lineTo(cursor + Vector2(-2.0, 0.0))
                //    close()
                //})

                // Option 3. Rectangular mesh. The only option that can be
                // deformed smoothly.
                drawer.pushTransforms()
                drawer.translate(x * 128.0 + 64, y * 72.0 + 36)
                drawer.vertexBuffer(rectMesh, DrawPrimitive.TRIANGLES)
                drawer.popTransforms()
            }
        }
    }
}

fun main() {
    application(
            Example(),
            configuration {
                width = 1280
                height = 720
            }
    )
}

