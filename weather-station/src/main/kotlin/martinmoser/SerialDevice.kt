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

    /**
     * Connect to the serial device.
     *
     * Try to connect to the serial device.
     * In total three tries are done.
     * If the connection still fails, the function returns false.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @returns True if the connection was successful. False otherwise.
     */
    fun connect(): Boolean {
        var tries = 0

        if (comPort.isOpen) {
            messageController.addMessage("Port is already connected!")
            return true
        }

        comPort.openPort()

        while (!comPort.isOpen && tries < 2) {
            messageController.addMessage("Connection to Arduino failed!")
            statusController.setStatus(Status.ERROR)

            comPort.openPort()
            tries++
        }

        if (!comPort.isOpen) return false

        messageController.addMessage("Succesfull connected to the Arduino on port ${comPort.systemPortName}!")
        statusController.setStatus(Status.CONNECTED)
        return true
    }

    /**
     * Disconnect from the serial device.
     *
     * Try to disconnect from the serial device.
     * In total three tries are done.
     * If the disconnection still fails, the function returns false.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @returns True if the disconnection was successful. False otherwise.
     */
    fun disconnect(): Boolean {
        var tries = 0

        if (!comPort.isOpen) {
            messageController.addMessage("Port is already disconnected!")
            return true
        }

        if (comPort.isOpen) comPort.closePort()

        while (comPort.isOpen && tries < 2) {
            messageController.addMessage("Trying again to close the port!")
            statusController.setStatus(Status.ERROR)

            comPort.closePort()
            tries++
        }

        if (comPort.isOpen) return false

        messageController.addMessage("Diconnected from the Arduino!")
        statusController.setStatus(Status.DISCONNECTED)
        return true
    }
}