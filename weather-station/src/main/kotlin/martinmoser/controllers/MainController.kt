package martinmoser.controllers

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import martinmoser.models.Sensor
import martinmoser.models.SensorList
import martinmoser.models.SensorModel
import tornadofx.Controller
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

    fun refresh() {
        model.commit()

        var x = sensors.toList()
        sensors.clear()
        sensors.addAll(x)
    }
}