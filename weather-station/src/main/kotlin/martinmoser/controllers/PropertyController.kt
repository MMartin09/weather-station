package martinmoser.controllers

import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.ini4j.Wini
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import java.io.File


/**
 * @todo Add description.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class PropertyController: Controller() {
    lateinit var ini: Wini

    //val decimalPlacesProperty = SimpleIntegerProperty(this, "decimal_places", 0)
    //var decimal_places by decimalPlacesProperty

    var decimal_places: Int = 0

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
