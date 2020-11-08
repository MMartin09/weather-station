package martinmoser

import com.fazecast.jSerialComm.SerialPort
import martinmoser.controllers.MessageController
import tornadofx.*

/**
 * Manager for serial devices.
 *
 * Provide functions for scanning (connected) serial devices and
 * searching for an Arduino.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SerialDeviceManager {
    private val messageController = find(MessageController::class)

    private var commPorts: MutableList<SerialPort?> = mutableListOf()
    var targetPort: SerialPort? = null
        private set

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
        if (scan) scanDevices()

        commPorts.forEach lit@{
            if (it == null) return@lit

            val portDescription = it.portDescription.toString()

            if (portDescription.contains("Arduino MKR WiFi 1010")) {
                messageController.addMessage("Found Arduino on Port: ${it.systemPortName}")
                targetPort = it

                return true
            }
        }

        // Called if no Arduino was found
        messageController.addMessage("Could not find any Arduino!")
        return false
    }
}