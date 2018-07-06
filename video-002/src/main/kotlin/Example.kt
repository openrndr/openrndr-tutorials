import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration

import org.openrndr.ffmpeg.FFMPEGVideoPlayer

/**
 * This is a basic example that shows how to use webcam imagery
 */
class Example : Program() {

    lateinit var videoPlayer: FFMPEGVideoPlayer

    override fun setup() {
        videoPlayer = FFMPEGVideoPlayer.fromDevice()
        videoPlayer.start()
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        videoPlayer.next()
        videoPlayer.draw(drawer)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 1280
        height = 720
    })
}