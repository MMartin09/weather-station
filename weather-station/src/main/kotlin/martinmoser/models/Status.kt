package martinmoser.models

/**
 * Class for representing the connection status.
 *
 * @author MMartin09
 * @since 0.1.0
 *
 * @param text String representing the status
 */
enum class Status(val text: String) {
    DISCONNECTED("Disconnected"),
    CONNECTED("Connected"),
    ERROR("Error")
}
