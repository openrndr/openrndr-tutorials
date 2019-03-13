import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.math.Vector2
import org.openrndr.shape.Composition
import org.openrndr.shape.CompositionDrawer
import org.openrndr.svg.saveToFile
import java.io.File

/**
 * CompositionDrawer001
 * Demonstrates the use of CompositionDrawer and Composition.saveToFile to
 * easily write SVG files.
 */
class Example: Program() {

    lateinit var composition: Composition
    override fun setup() {
        val compositionDrawer = CompositionDrawer()
        compositionDrawer.fill = null
        compositionDrawer.stroke = ColorRGBa.BLACK
        compositionDrawer.circle(Vector2(width/2.0, height/2.0), 100.0)

        composition = compositionDrawer.composition
        composition.saveToFile(File("output.svg"))
    }

    override fun draw() {
        drawer.background(ColorRGBa.PINK)
        drawer.composition(composition)
    }
}

fun main(args: Array<String>) {
    application(Example(), configuration{})
}