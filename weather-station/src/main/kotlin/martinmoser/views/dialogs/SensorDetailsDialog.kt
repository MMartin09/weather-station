package martinmoser.views.dialogs

import javafx.scene.Parent
import martinmoser.controllers.MainController
import martinmoser.models.Sensor
import martinmoser.models.SensorModel
import tornadofx.*

/**
 * Detail dialog for a sensor.
 *
 * Displays the information of the sensor.
 * For example, the name, value type, ...
 *
 * ToDo: Printing the enum seems somehow strange!
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SensorDetailsDialog: View("Sensor detail dialog") {
    private val mainController: MainController by inject()

    override val root = hbox {
        form {
            fieldset("Sensor details") {
                field("Name") {
                    textfield(mainController.model.name) {
                        isEditable = false
                    }
                }

                field("Value type") {
                    textfield(mainController.model.value_type.value.value.toString()) {
                        isEditable = false
                    }
                }

                field("Unit") {
                    textfield(mainController.model.unit) {
                        isEditable = false
                    }
                }
            }
        }
    }
}