import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.draw.*
import org.openrndr.filter.filterShaderFromUrl
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

/**
 * The Shadertoy buffer, abstraction over Shadertoy tab containing GLSL code.
 */
class ShadertoyBuffer : Filter(filterShaderFromUrl("file:shader-shadertoy-001/src/main/glsl/image.frag")) {
                     // for live shader code reloading use:
                     // Filter(null, filterWatcherFromUrl("file:shader-shadertoy-001/src/main/glsl/image.frag")) {

    var iResolution: Vector3 by parameters          // viewport resolution (in pixels)
    var iTime: Double by parameters                 // shader playback time (in seconds)
    var iTimeDelta: Double by parameters            // render time (in seconds)
    var iFrame: Int by parameters                   // shader playback frame
    var iChannelTime0: Double by parameters         // channel playback time (in seconds)
    var iChannelTime1: Double by parameters
    var iChannelTime2: Double by parameters
    var iChannelTime3: Double by parameters
    var iChannelResolution0: Double by parameters   // channel resolution (in pixels)
    var iChannelResolution1: Double by parameters
    var iChannelResolution2: Double by parameters
    var iChannelResolution3: Double by parameters
    var iMouse: Vector4 by parameters               // mouse pixel coords. xy: current (if MLB down), zw: click
    var iChannel0: ColorBuffer by parameters        // input channel. XX = 2D/Cube
    var iChannel1: ColorBuffer by parameters
    var iChannel2: ColorBuffer by parameters
    var iChannel3: ColorBuffer by parameters
    var iDate: Vector4 by parameters                // (year, month, day, time in seconds)
    var iSampleRate: Double by parameters           // sound sample rate (i.e., 44100)
}

/**
 * This is a basic example that shows how to use Shadertoy code.
 */
class Example : Program() {

    private var frameCounter: Int = 0

    lateinit var image: ShadertoyBuffer
    lateinit var imageOut: ColorBuffer

    override fun setup() {
        image = ShadertoyBuffer()
        imageOut = colorBuffer(width, height)
    }

    override fun draw() {
        populate(image)
        image.apply(arrayOf(), imageOut)
        drawer.image(imageOut)
        frameCounter++
    }

    private fun populate(buffer: ShadertoyBuffer) {
        buffer.iTime = seconds
        buffer.iResolution = Vector3(width.toDouble(), height.toDouble(), 0.0)
        buffer.iFrame = frameCounter
    }

}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}
