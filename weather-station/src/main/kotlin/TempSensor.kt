class TempSensor(
        name: String,
        value_type: ValueType,
        unit: String,
        value: Float,
        last_updated: Int
) : Sensor(name, value_type, unit, value, last_updated)
