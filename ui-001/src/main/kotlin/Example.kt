import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.panel.controlManager
import org.openrndr.panel.elements.button
import org.openrndr.panel.elements.clicked

/**
 * This is a basic example that shows how to create a single button
 */
class Example: Program() {

    var color = ColorRGBa.GRAY.shade(0.5)
    override fun setup() {
        val cm = controlManager {
            layout {
                button {
                    label = "click me"
                    // -- listen to the click event
                    clicked {
                        color = ColorRGBa(Math.random(), Math.random(), Math.random())
                    }
                }
            }
        }
        extend(cm) // <- this registers the control manager as a Program extension
    }

    override fun draw() {
        drawer.background(color)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}