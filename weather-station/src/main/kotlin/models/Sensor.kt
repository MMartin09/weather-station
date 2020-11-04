package models

import ValueType
import java.time.LocalDateTime

val EPOCH = LocalDateTime.of(1970, 1, 1, 0, 0)

open class Sensor(
        val name: String,
        val value_type: ValueType,
        val unit: String
) {
    var value: Float = Float.NaN
        get() = field
        set(value) {
            field = value

            // update the last updated mark
            last_updated = LocalDateTime.now()
        }

    var last_updated = EPOCH
        get() = field
}
