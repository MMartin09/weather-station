open class Sensor(
        val name: String,
        val value_type: ValueType,
        val unit: String,
        value: Float,
        last_updated: Int
) {
    var value: Float = value
        get() = field
        set(value) {
            field = value
            // Update last_updated
        }

    var last_updated: Int = last_updated
        get() = field
}
