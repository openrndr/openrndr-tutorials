import org.bytedeco.javacpp.avformat.av_register_all
import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration

import org.openrndr.ffmpeg.FFMPEGVideoPlayer

/**
 * This is a basic example that shows how to load and draw images
 */
class Example: Program() {

    lateinit var videoPlayer:FFMPEGVideoPlayer

    override fun setup() {
        videoPlayer = FFMPEGVideoPlayer.fromURL("file:video-001/data/openrndr.mp4")
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
        width = 640
        height = 360
    })
}