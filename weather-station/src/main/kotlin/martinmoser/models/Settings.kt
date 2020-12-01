package martinmoser.models

import tornadofx.ItemViewModel

/**
 * Settings class holds all settings for the application
 *
 * @author MMartin09
 * @since 0.1.0
 */
data class Settings(
    val decimal_places: Int,
    val database_host: String,
    val database_port: String,
    val database_name: String,
    val database_username: String,
    val database_password: String
)

/**
 * ItemViewModel for the Settings class.
 *
 * The model binds the required attributes from the settings.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SettingsModel : ItemViewModel<Settings>() {
    val decimal_places = bind(Settings::decimal_places)

    val database_host = bind(Settings::database_host)
    val database_port = bind(Settings::database_port)
    val database_name = bind(Settings::database_name)
    val database_username = bind(Settings::database_username)
    val database_password = bind(Settings::database_password)
}
