import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.panel.controlManager
import org.openrndr.panel.elements.*
import org.openrndr.panel.style.*

/**
 * This is a basic example that shows how to create a side bar with controls
 */
class Example: Program() {

    var color = ColorRGBa.GRAY.shade(0.25)
    var intensity = 1.0
    override fun setup() {
        val cm = controlManager {

            styleSheet(has class_ "container") { // <- this would be ".container" in CSS
                width = 150.px
                height = 100.percent // <- this forces the side-bar to be as high as the screen
                background = Color.RGBa(ColorRGBa.GRAY.shade(0.5))
                flexDirection = FlexDirection.Column // <- this forces the controls to be in column layout

                descendant(has type "button") { // <- this selects similar to ".container button" in CSS
                    width = 100.percent
                }
            }

            layout {
                div("container") {
                    button {
                        label = "click me"
                        // -- listen to the click event
                        clicked {
                            color = ColorRGBa(Math.random(), Math.random(), Math.random())
                        }
                    }

                    slider {
                        label = "slide me"
                        value = intensity
                        range = Range(0.0, 1.0)
                        events.valueChanged.subscribe {
                            intensity = it.newValue
                        }
                    }
                }
            }
        }
        extend(cm) // <- this registers the control manager as a Program extension
    }

    override fun draw() {
        drawer.background(color.shade(intensity))
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}