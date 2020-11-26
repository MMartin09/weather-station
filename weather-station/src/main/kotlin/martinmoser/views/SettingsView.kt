package martinmoser.views

import javafx.beans.property.SimpleIntegerProperty
import martinmoser.controllers.PropertyController
import martinmoser.models.Sensor
import martinmoser.models.Settings
import tornadofx.ItemViewModel
import tornadofx.*

/**
 * Settings view of the application
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SettingsView: View("Settings") {
    private val propertyController: PropertyController by inject()
    
    override val root = form {
        hbox {
            fieldset {
                vbox {
                    field("Decimal places") { textfield(propertyController.model.decimal_places) }
                }

                buttonbar {
                    button("Save") {
                        enableWhen(propertyController.model.dirty)

                        action {
                            propertyController.commit_changes()
                        }
                    }
                }
            }
        }
    }
}