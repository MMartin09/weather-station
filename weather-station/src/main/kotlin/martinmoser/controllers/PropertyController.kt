package martinmoser.controllers

import org.ini4j.Wini
import tornadofx.Controller
import java.io.File

/**
 * @author MMartin09
 * @since 0.1.0
 */
class PropertyController: Controller() {

    init {
        val config_file = File("config.properties")

        val init = Wini(config_file)

        val test = init.get("config", "DecimalPlaces", Int::class.javaPrimitiveType)
        println("$test")
    }

    fun initialize() {}
}