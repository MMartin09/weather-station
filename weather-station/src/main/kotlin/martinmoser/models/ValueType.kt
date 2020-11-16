package martinmoser.models

/**
 * Class for representing the value type of a sensor.
 *
 * @author MMartin09
 * @since 0.1.0
 */
enum class ValueType(value: String) {
    INTEGER("Integer"),
    FLOAT("Float"),
    BOOLEAN("Boolean")
}
