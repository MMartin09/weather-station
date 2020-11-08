package martinmoser.controllers

/**
 * Class for representing the connection status.
 *
 * @author MMartin09
 * @since 0.1.0
 */
enum class Status(val text: String) {
    DISCONNECTED("Disconnected"),
    CONNECTED("Connected"),
    ERROR("Error")
}