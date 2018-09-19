import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.extensions.Screenshots

/**
 * Screenshots001
 * Demonstrates the Screenshots extension
 * Screenshots are taken by pressing the space bar
 */
class Screenshots001: Program() {

    override fun setup() {
        // -- install the Screenshots extension
        extend(Screenshots().apply {
            // -- set up-scaling to 4.0
            scale = 4.0
        })
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.fill = ColorRGBa.BLACK
        drawer.stroke = ColorRGBa.WHITE
        drawer.circle(mouse.position, 100.0)
    }

}

fun main(args: Array<String>) {
    application(Screenshots001(), configuration {  })

}