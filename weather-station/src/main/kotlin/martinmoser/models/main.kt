package martinmoser.models

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import martinmoser.models.Sensor
import martinmoser.models.SensorModel
import tornadofx.*
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.math.roundToLong
import kotlin.random.Random.Default.nextFloat

fun main(args: Array<String>) {
    println("Hello World!")
/*
    val tempSensor = Sensor("Temp Sensor", martinmoser.models.ValueType.FLOAT, "°C")

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")

    tempSensor.value = 23.3F

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")
*/
    launch<MainApp>(args)
}

class MainApp: App(MainView::class)

class MainController: Controller() {
    val sensors = FXCollections.observableArrayList<Sensor>()
    var model = SensorModel()

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
    override val root = BorderPane()

    val mainController: MainController by inject()

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
        }

        with(root) {
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

                    mainController.model.rebindOnChange(this) {
                        selectedSensor -> item = selectedSensor ?: Sensor()

                        mainController.model.commit()
                    }
                }
            }
        }
    }
}

