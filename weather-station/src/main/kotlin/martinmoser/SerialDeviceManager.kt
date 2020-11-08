package martinmoser

import com.fazecast.jSerialComm.SerialPort
import martinmoser.controllers.MessageController
import tornadofx.*

/**
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SerialDeviceManager {
    val messageController = find(MessageController::class)

    private var commPorts: MutableList<SerialPort?> = mutableListOf()

    /**
     * Detect available serial devices.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    fun scanDevices() {
        messageController.addMessage("Searching for serial devices...")

        commPorts.clear()
        commPorts.addAll(SerialPort.getCommPorts())

        messageController.addMessage("Found ${commPorts.size} devices!")
    }

    /**
     * Search for an Arduino MKR 1010 WiFi.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param scan If true scanDevices will be called before searching. Defaults to false.
     *
     * @returns True if an Arduino was found. False otherwise.
     */
    fun searchArduino(scan: Boolean = false): Boolean {
        var comPort: SerialPort? = null

        if (scan) scanDevices()

        commPorts.forEach {
            val portDescription = it?.portDescription

            if (portDescription != null && portDescription.contains("Arduino MKR WiFi 1010")) {
                messageController.addMessage("Found Arduino on Port: ${it.systemPortName}")
                comPort = it
                return true
            }
        }

        if (comPort == null) {
            messageController.addMessage("Could not find any Arduino!")
            return false
        }

        return false
    }
}