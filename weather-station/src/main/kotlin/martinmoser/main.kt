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

object Sensors : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Sensors_ID")
}

object Cities : Table() {
    val id = integer("id").autoIncrement() // Column<Int>
    val name = varchar("name", 50) // Column<String>

    override val primaryKey = PrimaryKey(id, name = "PK_Cities_ID")
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
    //Database.connect("jdbc:sqlite:./data.db", "org.sqlite.JDBC")
    //Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")

    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Cities, Sensors)

        val id = Sensors.insert {
            it[name] = "Temp Sensor"
        } get Sensors.id

        println("New sensor id: $id")

        val iid = Sensors.insert {
            it[name] = "Hum Sensor"
        } get Sensors.id

        println("New sensor id: $iid")

        val sensors = Sensors.selectAll()

        val saintPetersburgId = Cities.insert {
            it[name] = "St. Petersburg"
        } get Cities.id

        val munichId = Cities.insert {
            it[name] = "Munich"
        } get Cities.id

        val pragueId = Cities.insert {
            it.update(name, stringLiteral("   Prague   ").trim().substring(1, 2))
        }[Cities.id]

        val pragueName = Cities.select { Cities.id eq pragueId }.single()[Cities.name]

        for (city in Cities.selectAll()) {
            println("${city[Cities.id]}: ${city[Cities.name]}")
        }

        if (sensors != null) {
            sensors.forEach {
                println("${it}")
            }
        }
    }

    launch<MainApp>(args)
}

class MainApp : App(MainView::class)
