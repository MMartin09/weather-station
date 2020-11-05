package martinmoser.models

import tornadofx.ItemViewModel
import tornadofx.getProperty
import java.time.LocalDateTime

val EPOCH = LocalDateTime.of(1970, 1, 1, 0, 0)

open class Sensor(
        var name: String,
        var value_type: Int,
        var unit: String
) {
    fun nameProperty() = getProperty(Sensor::name)
    fun valueTypeProperty() = getProperty(Sensor::value_type)
    fun unitProperty() = getProperty(Sensor::unit)

    /*var value: Float = Float.NaN
        get() = field
        set(value) {
            field = value

            // update the last updated mark
            last_updated = LocalDateTime.now()
        }

    var last_updated = EPOCH
        get() = field*/
}

class SensorModel: ItemViewModel<Sensor>() {
    val name = bind { item?.nameProperty() }
    val value_type = bind { item?.valueTypeProperty() }
    val unit = bind { item?.unitProperty() }
    //var value = bind { item?.value.toProperty() }
    //var last_updated = bind { item?.last_updated.toProperty() }
}