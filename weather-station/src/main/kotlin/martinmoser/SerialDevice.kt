package martinmoser

import com.fazecast.jSerialComm.SerialPort
import martinmoser.controllers.MessageController
import martinmoser.controllers.Status
import martinmoser.controllers.StatusController
import tornadofx.find


/**
 * General serial device.
 *
 * Provide functions for a serial device.
 * Serial device could only be an Arduino.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SerialDevice(port: SerialPort) {
    private val messageController = find(MessageController::class)
    private val statusController = find(StatusController::class)

    private val comPort = port

    fun connect(): Boolean {
        var tries = 0

        comPort.openPort()

        while (!comPort.isOpen && tries < 2) {
            messageController.addMessage("Connection to Arduino failed!")
            statusController.setStatus(Status.ERROR)

            comPort.openPort()
            tries++
        }

        if (!comPort.isOpen) return false

        messageController.addMessage("Succesfull connected to the Arduino on port ${comPort.systemPortName}")
        statusController.setStatus(Status.CONNECTED)
        return true
    }
}