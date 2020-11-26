package martinmoser.models

import tornadofx.ItemViewModel

/**
 * Settings class holds all settings for the application
 *
 * @author MMartin09
 * @since 0.1.0
 */
data class Settings(
    val decimal_places: Int
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
}
