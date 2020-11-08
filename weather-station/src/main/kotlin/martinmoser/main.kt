package martinmoser.models

import com.fazecast.jSerialComm.SerialPort
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import martinmoser.SerialDevice
import martinmoser.SerialDeviceManager
import martinmoser.controllers.MessageController
import martinmoser.controllers.Status
import martinmoser.controllers.StatusController
import tornadofx.*
import java.lang.Thread.sleep
import java.time.LocalTime
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random.Default.nextFloat


fun main(args: Array<String>) {
    println("Hello World!")

    launch<MainApp>(args)
}

class MainApp: App(MainView::class)

class MainController: Controller() {
    val sensors = FXCollections.observableArrayList<Sensor>()
    var model = SensorModel()

    val logText = SimpleStringProperty()

    init {
        sensors.add(Sensor("Temp Sensor", ValueType.FLOAT, "°C"))
        sensors.add(Sensor("Sensor 1", ValueType.FLOAT, "°C"))
        sensors.add(Sensor("Sensor 2", ValueType.FLOAT, "°C"))
    }

    fun refresh() {
        model.commit()

        var x = sensors.toList()
        sensors.clear()
        sensors.addAll(x)
    }
}

class MainView: View() {
    val mainController: MainController by inject()
    val messageController: MessageController by inject()
    private val statusController: StatusController by inject()

    init {
        statusController.setStatus(Status.DISCONNECTED)

        // create a daemon thread
        val timer = Timer("schedule", true);

        // schedule at a fixed rate
        timer.scheduleAtFixedRate(1000, 1000) {
            val sensor1 = mainController.sensors[0]
            sensor1.updateValue((nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor2 = mainController.sensors[1]
            sensor2.updateValue((nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor3 = mainController.sensors[2]
            sensor3.updateValue((nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()
        }
    }

    override val root = borderpane  {
        top {
            menubar {
                menu("File") {
                    item("Item 1")
                    item("Item 2")
                }

                menu("Edit") {
                    item("Item 1")
                    item("Item 2")

                    item("Connecto to Arduino").action {
                        val serialDeviceManager = SerialDeviceManager()
                        if (serialDeviceManager.searchArduino(scan = true)) {
                            // SearchArduino will return false if the targetPort is null.
                            // So no null safe call is required
                            val serialDevice = SerialDevice(serialDeviceManager.targetPort!!)
                            serialDevice.connect()
                        }
                    }
                }
            }
        }

        center {
            tableview<Sensor>(mainController.sensors) {
                column("Name", Sensor::name)
                column("Value Type", Sensor::value_type)
                column("Unit", Sensor::unit)

                column("Value", Sensor::value).cellFormat {
                    text = "%.${2}f".format(it)
                }

                column("Last updated", Sensor::last_updated).cellFormat {
                    text = "${it.hour}:" + "%02d".format(it.minute) + ":%02d".format(it.second)
                }

                mainController.model.rebindOnChange(this) { selectedSensor -> item = selectedSensor ?: Sensor()

                    mainController.model.commit()
                }
            }
        }

        bottom {
            vbox {
                textarea(messageController.getMessages())
                label(statusController.getStatus())
            }
        }
    }
}

