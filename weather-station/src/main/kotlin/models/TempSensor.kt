package models

import ValueType

class TempSensor(
        name: String,
        value_type: ValueType,
        unit: String,
) : Sensor(name, value_type, unit)
