import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.renderTarget

/**
 * This is a basic example that shows how to use render targets to draw to two off screen buffers which
 * can be composited into one image using transparency
 */
class Example: Program() {

    lateinit var rt0: RenderTarget
    lateinit var rt1: RenderTarget

    override fun setup() {
        // -- use the renderTarget builder to create render targets with color and depth attachments.
        rt0 = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }
        rt1 = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.stroke = null

        // -- bind the render target, clear it with a transparent background
        rt0.bind()
        drawer.background(ColorRGBa.TRANSPARENT)
        drawer.fill = ColorRGBa.WHITE.opacify(0.5)
        drawer.rectangle(40.0, 40.0, 80.0, 80.0)
        rt0.unbind()

        rt1.bind()
        drawer.background(ColorRGBa.TRANSPARENT)
        drawer.fill = ColorRGBa.PINK.opacify(0.5)
        drawer.rectangle(80.0,80.0,80.0,80.0)
        rt1.unbind()

        // composite the two images
        drawer.image(rt0.colorBuffer(0))
        drawer.image(rt1.colorBuffer(0))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}