package martinmoser.controllers

import javafx.beans.property.ListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import martinmoser.models.Sensor
import martinmoser.models.SensorList
import martinmoser.models.SensorModel
import tornadofx.Controller
import tornadofx.toProperty
import java.io.File

/**
 * Controller for the main view.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class MainController: Controller() {
    val sensors = FXCollections.observableArrayList<Sensor>()
    var model = SensorModel()

    init {
        val sensor_file = File("sensors.json").readText().replace("\n", "")
        val data = Json.decodeFromString<SensorList>(sensor_file)
        sensors.addAll(data.toAsi())
    }

    fun sensorNames(): ObservableList<String>? {
        val x = FXCollections.observableArrayList<String>()

        sensors.forEach{ x.add(it.name)}

        return x
    }

    /**
     * TODO: this function must be optimized and documented.
     */
    fun refresh() {
        //model.commit()

        var x = sensors.toList()
        sensors.clear()
        sensors.addAll(x)
    }

    /**
     * Get a SensorModel by the name of the seonsor.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param name Name of the sensor
     *
     * @returns SensorModel of the target sensor.
     */
    fun getSensorByName(name: String): Sensor? {
        sensors.forEach {
            if (it.name == name) {
                return it
            }
        }

        return null
    }
}