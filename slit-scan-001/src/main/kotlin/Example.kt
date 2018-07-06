import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.isolatedWithTarget
import org.openrndr.draw.renderTarget

import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.shape.Rectangle

/**
 * This is a basic example that shows how to use webcam imagery
 */
class Example : Program() {

    lateinit var videoPlayer: FFMPEGVideoPlayer
    var slitscan: RenderTarget? = null
    var frame: RenderTarget? = null
    override fun setup() {
        videoPlayer = FFMPEGVideoPlayer.fromDevice()
        videoPlayer.start()
    }

    var index = 0
    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        videoPlayer.next()

        if (slitscan == null && videoPlayer.width != 0 && videoPlayer.height != 0) {
            slitscan = renderTarget(videoPlayer.width, videoPlayer.height) {
                colorBuffer()
            }
            frame = renderTarget(videoPlayer.width, videoPlayer.height) {
                colorBuffer()
            }
        }


        val localFrame = frame
        val localSlitscan = slitscan


        if (localFrame != null && localSlitscan != null) {

            drawer.isolatedWithTarget(localFrame) {
                ortho(localFrame)
                videoPlayer.draw(drawer)
            }

            drawer.isolatedWithTarget(localSlitscan) {
                ortho(localSlitscan)
                val rectangle = Rectangle((index % localSlitscan.width).toDouble(), 0.0, 1.0, localFrame.height.toDouble())
                image(frame!!.colorBuffer(0), rectangle, rectangle)
            }
            index++

            drawer.image(localSlitscan.colorBuffer(0), 0.0, 0.0)
        }

    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 1280
        height = 720
    })
}