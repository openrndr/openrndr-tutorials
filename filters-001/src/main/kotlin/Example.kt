import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.colorBuffer
import org.openrndr.draw.renderTarget
import org.openrndr.filter.blur.BoxBlur

/**
 * This is a basic example that shows how to perform post-processing using filters
 */
class Example : Program() {

    lateinit var blur: BoxBlur
    lateinit var rt: RenderTarget
    lateinit var blurred: ColorBuffer

    override fun setup() {
        blur = BoxBlur()
        rt = renderTarget(width, height) {
            colorBuffer()
        }
        blurred = colorBuffer(width, height)
    }

    override fun draw() {

        drawer.withTarget(rt) {
            background(ColorRGBa.BLACK)
            fill = ColorRGBa.PINK
            circle(mouse.position, 100.0)
        }

        blur.window = 10
        blur.apply(rt.colorBuffer(0), blurred)
        drawer.image(blurred)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}