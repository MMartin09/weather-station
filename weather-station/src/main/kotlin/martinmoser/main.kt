package martinmoser.models

import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import kotlinx.serialization.Serializable
import martinmoser.views.MainView
import tornadofx.App
import tornadofx.beforeShutdown
import tornadofx.launch
import java.awt.event.WindowEvent
import java.util.*


@Serializable
data class SensorType(
    val name: String,
    val value_type: ValueType,
    val unit: String
)

@Serializable
class SensorList(
    val sensors: List<SensorType>
) {

    fun toAsi(): ArrayList<Sensor> {
        val sensorList: ArrayList<Sensor> = arrayListOf()

        sensors.forEach {
            sensorList.add(Sensor(it.name, it.value_type, it.unit))
        }

        return sensorList
    }
}

fun main(args: Array<String>) {
    launch<MainApp>(args)
}

class MainApp: App(MainView::class) 
