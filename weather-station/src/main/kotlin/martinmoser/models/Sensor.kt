package martinmoser.models

import javafx.beans.property.SimpleFloatProperty
import tornadofx.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.getProperty
import java.time.LocalDateTime

val EPOCH = LocalDateTime.of(1970, 1, 1, 0, 0)

/**
 * Sensor class.
 *
 * Defines a sensor with required attributes.
 * Each sensor has a unique id and name.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class Sensor(
        id: String? = null,
        name: String? = null,
        value_type: ValueType? = null,
        unit: String? = null
) {
    val idProperty = SimpleStringProperty(this, "id", id)
    var id by idProperty

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val valueTypeProperty = SimpleObjectProperty(this, "valuType", value_type)
    var value_type by valueTypeProperty

    val unitProperty = SimpleStringProperty(this, "unit", unit)
    var unit by unitProperty

    val valueProperty = SimpleFloatProperty(this, "value", 0F)
    var value by valueProperty

    val lastUpdatedProperty = SimpleObjectProperty(this, "last_updated", EPOCH)
    var last_updated by lastUpdatedProperty

    /**
     * Update the value of a sensor.
     * If the value of a sensor is updated with this function,
     * the last update attribut will also be refreshed.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param value New value of the sensor
     */
    fun updateValue(value: Float) {
        valueProperty.set(value)
        lastUpdatedProperty.set(LocalDateTime.now())
    }
}

/**
 * ItemViewModel for the Sensor class.
 *
 * The model binds the required attributes from the sensor.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SensorModel: ItemViewModel<Sensor>() {
    val id = bind(Sensor::idProperty)
    val name = bind(Sensor::nameProperty)
    val value_type = bind(Sensor::valueTypeProperty)
    val unit = bind(Sensor::unitProperty)
    val value = bind(Sensor::valueProperty)
    val last_updated = bind(Sensor::lastUpdatedProperty)
}
