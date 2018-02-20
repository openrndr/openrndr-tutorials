import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.renderTarget

/**
 * This is a basic example that shows how to use render targets to draw to off screen buffers.
 */
class Example: Program() {

    lateinit var rt: RenderTarget
    override fun setup() {

        // -- use the renderTarget builder to create a render target with color and depth attachments.
        rt = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)

        // -- bind the render target, clear it, draw a pink rectangle on it
        rt.bind()
        drawer.background(ColorRGBa.PINK)
        drawer.fill = ColorRGBa.WHITE
        drawer.stroke = null
        drawer.rectangle(40.0, 40.0, 80.0, 80.0)
        rt.unbind()

        // the color buffer attached to the render target can be used as an image
        drawer.image(rt.colorBuffer(0))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}