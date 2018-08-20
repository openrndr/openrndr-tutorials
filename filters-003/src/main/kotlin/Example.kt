import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.filter.filterWatcherFromUrl

/**
 * Example noise filter
 */
class Noise : Filter(watcher = filterWatcherFromUrl("file:data/shaders/noise.frag")) {
    var gain: Double by parameters
    var time: Double by parameters

    init {
        gain = 1.0
    }
}

/**
 * This is a basic example that shows how to perform post-processing using filters and shader watchers
 */
class Example : Program() {

    lateinit var noise: Noise
    lateinit var rt: RenderTarget
    lateinit var distorted: ColorBuffer

    override fun setup() {
        noise = Noise()
        rt = renderTarget(width, height) {
            colorBuffer()
        }
        distorted = colorBuffer(width, height)
    }

    override fun draw() {

        drawer.withTarget(rt) {
            background(ColorRGBa.BLACK)
            fill = ColorRGBa.PINK
            circle(mouse.position, 100.0)
        }

        noise.gain = Math.cos(seconds) * 0.5 + 0.5
        noise.time = seconds
        noise.apply(rt.colorBuffer(0), distorted)
        drawer.image(distorted)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}