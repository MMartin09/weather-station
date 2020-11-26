package martinmoser.controllers

import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import martinmoser.models.SettingsModel
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
    val model = SettingsModel()
    private lateinit var ini: Wini

    init {
        val config_file = File("config.properties")

        ini = Wini(config_file)

        // Read settings values
        get_decimal_places()

        model.commit()
    }

    private fun get_decimal_places() {
        val value = ini.get("Config", "DecimalPlaces", Int::class.javaPrimitiveType)

        if (value != 0) decimal_places(value)
        else decimal_places(2)
    }

    fun commit_changes() {
        model.commit()
    }

    fun decimal_places(): Int = model.decimal_places.getValue()
    fun decimal_places(value: Int) {model.decimal_places.setValue(value)}
}
