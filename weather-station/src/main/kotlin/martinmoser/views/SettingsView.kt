package martinmoser.views

import javafx.beans.property.SimpleIntegerProperty
import martinmoser.controllers.PropertyController
import martinmoser.models.Sensor
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

    data class Settings(val decimal_places: Int)

    private val model = object: ItemViewModel<Settings>() {
        val decimal_places = bind(Settings::decimal_places)

        override fun onCommit() {
            item = Settings(decimal_places.value)
        }
    }

    init {
        model.decimal_places.setValue(propertyController.decimal_places())
        model.commit()
    }

    override val root = form {
        hbox {
            fieldset {
                vbox {
                    field("Decimal places") { textfield(model.decimal_places) }
                }

                buttonbar {
                    button("Save") {
                        enableWhen(model.dirty)

                        action {
                            println("Model: ${model.isDirty(model.decimal_places)}")
                            model.commit()
                            println("Model: ${model.isDirty(model.decimal_places)}")
                            println("- - - - - - - - - - - -")

                        }
                    }
                }
            }
        }
    }
}