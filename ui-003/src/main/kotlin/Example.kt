import org.openrndr.Application
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.panel.controlManager
import org.openrndr.panel.elements.*
import org.openrndr.panel.style.*

/**
 * This is a basic example that shows how to create a side bar with controls
 */
class Example: Program() {

    var sliders = mutableListOf<Slider>()
    var intensity = 1.0
    var labels = listOf("Today", "brings", "the", "public", "release", "of the", "Panel", "library", "for", "OPENRNDR.", "",
            "Panel", "is a", "library", "intended", "for the", "creation", "of", "simple", "and not", "so simple", "user", "interfaces.", "",
            "Panel", "uses", "html+css-like", "layouting", "and", "styling", "through", "Kotlin's", "type safe", "builder", "pattern.", "",
            "Documentation", "and", "examples", "are", "found","in the", "OPENRNDR", "guide", "", "", "", "", "", "", "", "")


    override fun setup() {
        val cm = controlManager {


            styleSheet(has class_ "outer") {
                display = Display.FLEX
                flexDirection = FlexDirection.Row
                width = 100.percent
                height = 100.percent
                marginRight = 20.px
                marginLeft = 20.px
                marginTop = 20.px
                marginBottom = 20.px
                background = Color.RGBa(ColorRGBa.GRAY.shade(0.5))
                child(has class_ "container") {
                    width = (560/4.0).px
                    height = 100.percent // <- this forces the side-bar to be as high as the screen
                    flexDirection = FlexDirection.Column // <- this forces the controls to be in column layout
                    descendant(has type "button") { // <- this selects similar to ".container button" in CSS
                        width = 100.percent
                    }
                }
            }

            var index = 0




            layout {
                div("outer") {
                    for (c in 0..3) {
                        div("container") {
                            for (i in 0 until 13) {
                                sliders.add(slider {
                                    label = labels[index]
                                    index++
                                    value = intensity
                                    range = Range(0.0, 1.0)
                                    events.valueChanged.subscribe {
                                        intensity = it.newValue
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
        extend(cm) // <- this registers the control manager as a Program extension
    }

    override fun draw() {
        val duration = (labels.size/2.5)

        val t = seconds
        sliders.forEachIndexed { index, it ->
            it.value = Math.cos( Math.PI*4.0*(index*1.0/labels.size) + (t/duration)*Math.PI*16) * 0.5 + 0.5
            it.label = labels[(index + (seconds*2.5).toInt())%labels.size].toUpperCase()
        }
        if (seconds*2.5 >= labels.size) {
            application.exit()
        }
        drawer.background(ColorRGBa.PINK)
    }
}

fun main(args: Array<String>) {
    Application.run(Example(), configuration {
        width = 600
        height = 600
    })
}