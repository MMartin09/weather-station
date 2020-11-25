package martinmoser.controllers

import org.ini4j.Wini
import tornadofx.Controller
import java.io.File


/**
 * @todo Add description.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class PropertyController: Controller() {
    lateinit var ini: Wini

    private var decimal_places: Int = 0

    init {
        val config_file = File("config.properties")

        ini = Wini(config_file)

        get_decimal_places()
    }

    fun get_decimal_places() {
        val value = ini.get("Config", "DecimalPlaces", Int::class.javaPrimitiveType)

        if (value != 0) decimal_places(value)
        else decimal_places(2)
    }

    fun decimal_places(): Int = decimal_places
    fun decimal_places(value: Int) {decimal_places = value}
}