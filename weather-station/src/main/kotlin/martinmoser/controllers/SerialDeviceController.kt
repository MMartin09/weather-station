package martinmoser.controllers

import martinmoser.SerialDevice
import martinmoser.SerialDeviceManager
import tornadofx.Controller

/**
 * Controller for the serial device.
 *
 * The controller is used to set the status message in the main window.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class SerialDeviceController : Controller() {
    private var serialDevice: SerialDevice? = null
    private val serialDeviceManager = SerialDeviceManager()

    fun connect(): Boolean {
        val foundArduino = serialDeviceManager.searchArduino(true)

        if (foundArduino) {
            val serialPort = serialDeviceManager.targetPort!!

            serialDevice = SerialDevice(serialPort)
            return serialDevice!!.connect()
        }

        return false
    }

    fun disconnect(): Boolean {
        return serialDevice!!.disconnect()
    }
}
