package martinmoser.models

import kotlinx.serialization.Serializable
import martinmoser.views.MainView
import tornadofx.App
import tornadofx.launch
import java.util.*

@Serializable
data class SensorType(
    val id: String,
    val name: String,
    val value_type: ValueType,
    val unit: String
)

@Serializable
class SensorList(
    val sensors: List<SensorType>
) {
    fun toSensor(): ArrayList<Sensor> {
        val sensorList: ArrayList<Sensor> = arrayListOf()

        sensors.forEach {
            sensorList.add(Sensor(it.id, it.name, it.value_type, it.unit))
        }

        return sensorList
    }
}

/**
 * Main function.
 *
 * Launches the Gui application.
 *
 * @author MMartin09
 * @since 0.1.0
 */
fun main(args: Array<String>) {
    launch<MainApp>(args)
}

class MainApp: App(MainView::class)
