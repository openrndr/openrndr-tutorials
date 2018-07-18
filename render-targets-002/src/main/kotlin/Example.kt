import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.renderTarget
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to use render targets to draw to two off screen buffers which
 * can be composited into one image using transparency
 */
class Example: Program() {

    lateinit var rt0: RenderTarget
    lateinit var rt1: RenderTarget

    override fun setup() {
        // -- use the renderTarget builder to create render targets with color and depth attachments.
        rt0 = renderTarget(width, height, window.scale.x) {
            colorBuffer()
            depthBuffer()
        }
        rt1 = renderTarget(width, height,window.scale.x) {
            colorBuffer()
            depthBuffer()
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.stroke = null

//        // -- bind the render target, clear it with a transparent background
        rt0.bind()
//        drawer.background(ColorRGBa.GREEN)
//        drawer.fill = ColorRGBa.WHITE.opacify(0.5)
//        drawer.rectangle(40.0, 40.0, 80.0, 80.0)
        rt0.unbind()
//
//        rt1.bind()
//        drawer.background(ColorRGBa.GREEN)
//        drawer.fill = ColorRGBa.PINK.opacify(0.5)
//        drawer.rectangle(80.0,80.0,80.0,80.0)
//        rt1.unbind()
//
//        drawer.background(ColorRGBa.PINK)
        // composite the two images
        drawer.image(rt0.colorBuffer(0), Rectangle(0.0, 0.0, width*1.0, height*1.0),Rectangle(0.0, 0.0, width*1.0, height*1.0))
        //drawer.image(rt1.colorBuffer(0), 0.0, 0.0)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 640
        height = 480
    })

}