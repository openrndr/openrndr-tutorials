import kotlinx.coroutines.channels.*
import kotlinx.coroutines.runBlocking

import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.colorBuffer
import org.openrndr.draw.launch
import org.openrndr.draw.renderTarget
import org.openrndr.filter.blend.passthrough
import org.openrndr.internal.Driver

class Coroutines001 : Program() {
    override fun setup() {
        // -- we need a nicer api for this in the near future
        val drawThread = Driver.driver.createDrawThread()
        val result = Channel<ColorBuffer>()
        val go = Channel<Unit>()

        val copy = colorBuffer(512, 512)

        drawThread.launch {
            val rt = renderTarget(512, 512) {
                colorBuffer()
            }

            while (true) {
                runBlocking {
                    go.receive()
                }
                drawThread.drawer.withTarget(rt) {
                    ortho(rt)
                    background(ColorRGBa(Math.random(), Math.random(), Math.random()))
                    fill = ColorRGBa.PINK
                    for (i in 0 until 2) {
                        circle(Math.random() * 512, Math.random() * 512, 20.0)
                    }
                }
                // -- probably withTarget could include finish() calls in the future
                Driver.driver.finish()
                result.send(rt.colorBuffer(0))
            }
        }

        // -- send the initial go to kickstart the off-thread renderer
        runBlocking {
           go.send(Unit)
        }

        extend {
            if (!result.isEmpty) {
                runBlocking {
                    val image = result.receive()
                    passthrough.apply(image, copy)
                    Driver.driver.finish()
                    go.send(Unit)
                }
            }
            drawer.background(ColorRGBa.GREEN)
            drawer.image(copy)
            drawer.fill = ColorRGBa.WHITE
            drawer.circle(Math.cos(seconds)*width/2.0 + width/2.0, height/2.0, 20.0)
        }
    }
}

fun main() = application(Coroutines001(), configuration { })