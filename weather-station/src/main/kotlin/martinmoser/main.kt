package martinmoser.models

import kotlinx.serialization.Serializable
import martinmoser.views.MainView
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.App
import tornadofx.launch

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
    /*Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Sensors)

        val id = Sensors.insert {
            it[name] = "Temp Sensor"
        } get Sensors.id

        val iid = Sensors.insert {
            it[name] = "Hum Sensor"
        } get Sensors.id

        for (sensor in Sensors.selectAll()) {
            println("${sensor[Sensors.id]}: ${sensor[Sensors.name]}")
        }

    }*/

    launch<MainApp>(args)
}

class MainApp : App(MainView::class)
