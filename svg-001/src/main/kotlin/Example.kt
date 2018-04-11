import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.shape.Composition
import org.openrndr.svg.loadSVG
import java.io.File

/**
 * This is a basic example that shows how to load and draw SVG files
 */
class Example: Program() {

    lateinit var composition: Composition

    override fun setup() {
        composition = loadSVG(File("data/svg/composition.svg").readText())
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        drawer.composition(composition)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}