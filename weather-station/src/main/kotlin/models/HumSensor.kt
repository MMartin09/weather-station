package models

import ValueType

class HumSensor(
        name: String,
        value_type: ValueType,
        unit: String
) : Sensor(name, value_type, unit)