import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorXSVa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.colorBuffer

/**
 * This is a basic example that shows color basics, specifically the conversion from HSV to RGB color space
 */
class Example: Program() {
    lateinit var hsvImage: ColorBuffer
    lateinit var hsvWheel: ColorBuffer

    lateinit var xsvImage: ColorBuffer
    lateinit var xsvWheel: ColorBuffer

    override fun setup() {
        hsvImage = colorBuffer(200, 200)
        hsvWheel = colorBuffer(200, 200)

        xsvImage = colorBuffer(200, 200)
        xsvWheel = colorBuffer(200, 200)
    }

    override fun draw() {

        // -- create a typical HSV image
        run {
            val saturation = 1.0 * mouse.position.y / height
            for (y in 0 until 200) {
                for (x in 0 until 200) {
                    val hue = 360.0 * x / 200.0
                    val value = 1.0 - y / 200.0
                    hsvImage.shadow[x, y] = ColorHSVa(hue, saturation, value).toRGBa()
                }
            }
            hsvImage.shadow.upload()
        }
        drawer.image(hsvImage)


        run {
            val saturation = 1.0 * mouse.position.y / height
            for (y in 0 until 200) {
                for (x in 0 until 200) {

                    val dx = x - 100.0
                    val dy = y - 100.0
                    val hue = Math.toDegrees(Math.atan2(dy, dx)).let { if (it < 0) it + 360 else it }

                    val value = Math.sqrt(dx * dx + dy * dy) / 100.0
                    if (value <= 1.0)
                    hsvWheel.shadow[x, y] = ColorHSVa(hue, saturation, value).toRGBa()
                }
            }
            hsvWheel.shadow.upload()
        }
        drawer.image(hsvWheel, 220.0, 0.0)


        drawer.translate(0.0, 220.0)
        // -- create a XSV image, this is roughly the Adobe Kuler model
        run {
            val saturation = 1.0 * mouse.position.y / height
            for (y in 0 until 200) {
                for (x in 0 until 200) {
                    val hue = 360.0 * x / 200.0
                    val value = 1.0 - y / 200.0
                    xsvImage.shadow[x, y] = ColorXSVa(hue, saturation, value).toRGBa()
                }
            }
            xsvImage.shadow.upload()
        }


        drawer.image(xsvImage)

        run {
            val saturation = 1.0 * mouse.position.y / height
            for (y in 0 until 200) {
                for (x in 0 until 200) {

                    val dx = x - 100.0
                    val dy = y - 100.0
                    val hue = Math.toDegrees(Math.atan2(dy, dx)).let { if (it < 0) it + 360 else it }

                    val value = Math.sqrt(dx * dx + dy * dy) / 100.0
                    if (value <= 1.0)
                        xsvWheel.shadow[x, y] = ColorXSVa(hue, saturation, value).toRGBa()
                }
            }
            xsvWheel.shadow.upload()
        }
        drawer.image(xsvWheel, 220.0, 0.0)


    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}