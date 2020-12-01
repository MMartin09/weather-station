package martinmoser.controllers

import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller
import kotlin.random.Random.Default.nextFloat

object Sensors : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Sensors_ID")
}

object SensorValues : Table() {
    val id = integer("id").autoIncrement()
    val sensorId = (integer("sensor_id") references Sensors.id).nullable()
    val value = float("value")

    override val primaryKey = PrimaryKey(SensorValues.id, name = "PK_Sensor_Values_ID")
}

class DatabaseController : Controller() {
    private var db: Database? = null

    /**
     * Connect to the database.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    fun connect(): Boolean {
        db = Database.connect(
            url = "jdbc:postgresql://127.0.0.1:5432/weather_station",
            driver = "org.postgresql.Driver",
            user = "root",
            password = "passwd"
        )

        if (db == null) return false
        return true
    }

    /**
     * Create the database schema.
     *
     * This function is required for in-memory databases to create the schema.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    fun create_schema() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Sensors)
            SchemaUtils.create(SensorValues)
        }
    }

    fun add_sensors(sensors: ObservableList<String>) {
        transaction {
            val existing_sensors = Sensors.selectAll().toList()

            sensors.forEach loop@{
                val sensor_name = it

                val count = Sensors.select { Sensors.name eq sensor_name }.count()

                if (count > 0) return@loop

                Sensors.insert {
                    it[name] = sensor_name
                }
            }

            for (sensor in Sensors.selectAll()) {
                println("${sensor[Sensors.id]}: ${sensor[Sensors.name]}")
            }
        }
    }

    fun test(name: String) {
        println("Test")
        transaction {
            val id = Sensors.select { Sensors.name eq name}.map {
                it[Sensors.id]
            }[0]

            SensorValues.insert {
                it[sensorId] = id
                it[value] = nextFloat()
            }

            println("$name id = $id")
        }
    }
}
