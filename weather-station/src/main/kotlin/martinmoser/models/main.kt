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
        sensors.add(Sensor("Temp Sensor", ValueType.INTEGER, "°C"))
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
            val sensor = mainController.model

            val sensor1 = mainController.sensors[0]
            sensor1.valueProperty.set((nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()

            if (mainController.model.item != null) {
                sensor.name.set("Test")
                sensor.value.set(12.0F)

                sensor1.nameProperty.set("TResr")

                mainController.model.commit()
                mainController.refresh()

                mainController.sensors[0].nameProperty.set("Test")

                if (mainController.model.isDirty) {
                    mainController.model.commit()

                    mainController.refresh()
                }
            }
        }

        with(root) {
            center {
                tableview<Sensor>(mainController.sensors) {
                    column("Name", Sensor::name)
                    column("Value Type", Sensor::value_type)
                    column("Unit", Sensor::unit)
                    column("Value", Sensor::value)
                    column("Last updated", Sensor::last_updated)

                    mainController.model.rebindOnChange(this) {
                        selectedSensor -> item = selectedSensor ?: Sensor()

                        mainController.model.commit()
                    }
                }
            }
        }
    }
}

