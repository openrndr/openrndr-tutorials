import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap


private var fullScreen: Boolean? = null

/**
 * This is a simple dialog program
 */
class Dialog:Program() {

    init {
        mouse.buttonDown.listen {
            fullScreen = mouse.position.x > 320

            application.exit()
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/Roboto-Medium.ttf", 20.0)
        drawer.fill = ColorRGBa.BLACK
        drawer.text("windowed", 100.0, height/2.0)
        drawer.text("fullscreen", width-200.0, height/2.0)
    }
}

/**
 * This is a simple main program
 */
class Example: Program() {
    override fun draw() {
        drawer.background(ColorRGBa.PINK)

        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/Roboto-Medium.ttf", 20.0)
        drawer.fill = ColorRGBa.BLACK

        if (fullScreen == true) {
            drawer.text("fullscreen", width/2.0, height/2.0)
        } else {
            drawer.text("windowed", width/2.0, height/2.0)

        }
    }
}

fun main(args: Array<String>) {
    Application.run(Dialog(), configuration {
        width = 640
        height = 200
    })

    fullScreen?.let {
        Application.run(Example(), configuration {
            fullscreen = it
            width = if (it) -1 else 640
            height = if (it) -1 else 480
        })
    }


}