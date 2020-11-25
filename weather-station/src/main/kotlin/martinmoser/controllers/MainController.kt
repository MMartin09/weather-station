package martinmoser.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tornadofx.Controller
import java.io.File

import martinmoser.controllers.PropertyController
import martinmoser.models.Sensor
import martinmoser.models.SensorList
import martinmoser.models.SensorModel

/**
 * Controller for the main view.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class MainController: Controller() {
    val sensors = FXCollections.observableArrayList<Sensor>()
    var model = SensorModel()

    // Initialize the property controller
    val propertyController: PropertyController by inject()

    init {
        val sensor_file = File("sensors.json").readText().replace("\n", "")
        val data = Json.decodeFromString<SensorList>(sensor_file)
        sensors.addAll(data.toSensor())

        propertyController.initialize()
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
     * Get the sensor index by its id.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param id Id of the sensor
     *
     * @returns Sensor index in the list
     */
    fun getIndexById(id: String): Int? {
        var i = 0

        sensors.forEach {
            if (it.id == id) return i
            i++
        }

        return null
    }

    /**
     * Get a Sensor by the name of the seonsor.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param name Name of the sensor
     *
     * @returns Sensor of the target sensor.
     */
    fun getSensorByName(name: String): Sensor? {
        sensors.forEach {
            if (it.name == name) {
                return it
            }
        }

        return null
    }

    /**
     * Get a sensor by its index.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param index Index of the sensor in the list
     *
     * @returns Sensor at index
     */
    fun getSensorByIndex(index: Int): Sensor? {
        if (index >= sensors.size) return null
        return sensors[index]
    }
}