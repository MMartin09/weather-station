package martinmoser

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import martinmoser.controllers.MainController
import martinmoser.controllers.MessageController
import martinmoser.controllers.StatusController
import martinmoser.models.Status
import tornadofx.find
import java.io.BufferedReader
import java.io.InputStreamReader


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
    private val mainController = find(MainController::class)
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

        val input = BufferedReader(InputStreamReader(comPort.getInputStream()));

        while (!comPort.isOpen && tries < 3) {
            messageController.addMessage("Connection to Arduino failed!")
            statusController.setStatus(Status.ERROR)

            comPort.openPort()
            tries++
        }

        if (!comPort.isOpen) return false

        comPort.addDataListener(object : SerialPortDataListener {
            override fun getListeningEvents(): Int {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
            }

            override fun serialEvent(event: SerialPortEvent) {
                if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return  //wait until we receive data
                val newData = ByteArray(comPort.bytesAvailable()) //receive incoming bytes
                comPort.readBytes(newData, newData.size.toLong()) //read incoming bytes
                val serialData = String(newData) //convert bytes to string

                val id = serialData.split("=")[0]
                val value = serialData.split("=")[1].toFloat()

                val sensor_index = mainController.getIndexById(id)

                if (sensor_index == null) {
                    // TODO show error that sensor id was not found!
                    println("Could not find sensor with id: $id")
                    return
                }

                val sensor = mainController.sensors[sensor_index]
                sensor.updateValue(value)

                mainController.refresh()
            }
        })

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

        while (comPort.isOpen && tries < 3) {
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