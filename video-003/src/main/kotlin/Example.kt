import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.renderTarget

import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.filter.blur.BoxBlur

/**
 * This is a basic example that shows how to load and draw a video with a blur filter applied
 * Combining examples video-001, render-targets-001 and filters-001
 */
class Example: Program() {

    lateinit var videoPlayer: FFMPEGVideoPlayer
    lateinit var rt: RenderTarget
    lateinit var blur: BoxBlur

    override fun setup() {
        videoPlayer = FFMPEGVideoPlayer.fromURL("file:data/openrndr.mp4")
        videoPlayer.start()

        // -- use the renderTarget builder to create a render target with color and depth attachments.
        rt = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }

        blur = BoxBlur()
        blur.window = 10
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)

        // -- bind the render target, clear it with black, draw a video frame on it
        drawer.withTarget(rt) {
            drawer.background(ColorRGBa.BLACK)
            videoPlayer.next()
            videoPlayer.draw(drawer)
        }

        // -- apply a blur on the render target's color attachment
        blur.apply(rt.colorBuffer(0), rt.colorBuffer(0))

        // -- draw the blurred color attachment which can be used as an image
        drawer.image(rt.colorBuffer(0))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 640
        height = 360
    })
}
