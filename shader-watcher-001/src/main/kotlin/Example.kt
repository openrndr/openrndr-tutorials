import org.openrndr.Application
import org.openrndr.Configuration
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ShaderWatcher
import org.openrndr.draw.shaderWatcher
import java.io.File

/**
 * This is a basic example that shows how to use the ShaderWatcher
 * Shader watchers are useful in scenarios in which you want to create a shader from files and recreate the shader
 * after changes have been made to those files.
 */
class Example: Program() {
    lateinit var watcher: ShaderWatcher
    override fun setup() {
        watcher = shaderWatcher {
            fragmentShaderUrl = "file:data/shaders/watch-me.frag"
            vertexShaderUrl = "file:data/shaders/watch-me.vert"
        }
    }

    override fun draw() {
        drawer.background(ColorRGBa.BLACK)
        watcher.shader

    }
}

fun main(args: Array<String>) {
    Application.run(Example(), Configuration())
}