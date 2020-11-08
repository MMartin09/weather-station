package martinmoser.models

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import martinmoser.controllers.MessageController
import tornadofx.*
import java.time.LocalTime
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random.Default.nextFloat


fun main(args: Array<String>) {
    println("Hello World!")

    /*val commPorts = SerialPort.getCommPorts()

    println("Found ${commPorts.size} comm ports! \n")

    var comPort: SerialPort? = null

    commPorts.forEach {
        val portDescription = it.portDescription

        if (portDescription.contains("Arduino MKR WiFi 1010")) {
            println("Found Arduino on Port: ${it.systemPortName}")
            comPort = it
            return
        }
    }

    if (comPort == null) {
        println("Could not find any Arduino!")
        return
    }*/

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

    init {
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

            // -----------------------------------

            //mainController.logText.set("Test ${nextFloat()}")

            messageController.addMessage("Test ${nextFloat()}")
        }
    }

    override val root = borderpane  {
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
            textarea(messageController.getMessages())
        }
    }
}

