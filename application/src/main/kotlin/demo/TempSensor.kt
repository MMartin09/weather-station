package demo

class TempSensor(
        name: String,
        value_type: Int,
        unit: String,
        value: Float,
        last_updated: Int
) : Sensor(name, value_type, unit, value, last_updated)
