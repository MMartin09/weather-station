package martinmoser.views

import tornadofx.View
import tornadofx.hbox
import tornadofx.label

/**
 * Settings view of the application
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SettingsView: View() {

    override val root = hbox {
        label("Settings")
    }
}