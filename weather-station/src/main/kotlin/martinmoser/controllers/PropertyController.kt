package martinmoser.controllers

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
class PropertyController : Controller() {
    val model = SettingsModel()
    private lateinit var ini: Wini

    private val configSection = "Config"
    private val databaseSection = "Database"

    init {
        val config_file = File("config.properties")

        ini = Wini(config_file)

        // Read settings values
        get_decimal_places()

        model.commit()
    }

    private fun get_decimal_places() {
        val value = ini.get(configSection, "DecimalPlaces", Int::class.javaPrimitiveType)

        if (value != 0) decimal_places(value)
        else decimal_places(2)
    }

    private fun get_database_host() {
        val value = ini.get(databaseSection, "Host", String::class.javaPrimitiveType)

        if (value != null) database_host(value)
        else database_host("")
    }

    private fun get_database_port() {
        val value = ini.get(databaseSection, "Port", String::class.javaPrimitiveType)

        if (value != null) database_port(value)
        else database_port("")
    }

    private fun get_database_name() {
        val value = ini.get(databaseSection, "Database", String::class.javaPrimitiveType)

        if (value != null) database_name(value)
        else database_name("")
    }

    private fun get_database_username() {
        val value = ini.get(databaseSection, "Username", String::class.javaPrimitiveType)

        if (value != null) database_username(value)
        else database_username("")
    }

    private fun get_database_password() {
        val value = ini.get(databaseSection, "Password", String::class.javaPrimitiveType)

        if (value != null) database_password(value)
        else database_password("")
    }

    fun commit_changes() {
        model.commit()
    }

    fun decimal_places(): Int = model.decimal_places.getValue()
    fun decimal_places(value: Int) { model.decimal_places.setValue(value) }

    fun database_host(): String = model.database_host.getValue()
    fun database_host(value: String) { model.database_host.setValue(value) }

    fun database_port(): String = model.database_port.getValue()
    fun database_port(value: String) { model.database_port.setValue(value) }

    fun database_name(): String = model.database_name.getValue()
    fun database_name(value: String) { model.database_name.setValue(value) }

    fun database_username(): String = model.database_username.getValue()
    fun database_username(value: String) { model.database_username.setValue(value) }

    fun database_password(): String = model.database_password.getValue()
    fun database_password(value: String) { model.database_password.setValue(value) }
}
