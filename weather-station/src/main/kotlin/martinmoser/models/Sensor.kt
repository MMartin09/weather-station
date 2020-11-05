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

open class Sensor(
        name: String? = null,
        value_type: ValueType? = null,
        unit: String? = null
) {
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
}

class SensorModel: ItemViewModel<Sensor>() {
    val name = bind(Sensor::nameProperty)
    val value_type = bind(Sensor::valueTypeProperty)
    val unit = bind(Sensor::unitProperty)
    val value = bind(Sensor::valueProperty)
    val last_updated = bind(Sensor::lastUpdatedProperty)
}