package martinmoser

import martinmoser.controllers.MainController
import martinmoser.controllers.PropertyController
import martinmoser.models.ValueType
import tornadofx.find

/**
 * Helper class for the formatting of the sensor values.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class ValueFormatter {
    val mainController = find(MainController::class)
    private val propertyController =  find(PropertyController::class)

    val decimal_places = propertyController.decimal_places()

    /**
     * Create the formatted value string of a sensor value.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param index The index of the target sensor
     *
     * @returns The formated value as string
     */
    fun getFormatted(index: Int): String {
        val sensor = mainController.getSensorByIndex(index)

        if (sensor == null) return ""

        if (sensor.value_type == ValueType.FLOAT) return formatFloat(sensor.value)
        if (sensor.value_type == ValueType.INTEGER) return formatInteger(sensor.value)
        if (sensor.value_type == ValueType.BOOLEAN) return formatBoolean(sensor.value)

        return ""
    }

    /**
     * Create the formatted string for ValueType FLOAT
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param value Current value of the sensor as floating point
     *
     * @returns The formatted value as string.
     */
    private fun formatFloat(value: Float): String {
        return "%.${decimal_places}f".format(value)
    }

    /**
     * Create the formatted string for ValueType INTEGER
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param value Current value of the sensor as floating point
     *
     * @returns The formatted value as string.
     */
    private fun formatInteger(value: Float): String {
        return "%.${0}f".format(value)
    }

    /**
     * Create the formatted string for ValueType BOOLEAN
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param value Current value of the sensor as floating point
     *
     * @returns The formatted value as string.
     */
    private fun formatBoolean(value: Float): String {
        if (value < 1) return "False"
        else return "True"
    }
}