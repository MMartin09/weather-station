package martinmoser.controllers

import javafx.collections.ObservableList
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException
import tornadofx.Controller
import java.net.ConnectException
import java.sql.BatchUpdateException
import java.sql.SQLException
import java.sql.SQLIntegrityConstraintViolationException
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
    private val propertyController: PropertyController by inject()

    private var db: Database? = null

    private val host: String
    private val port: String
    private val database: String
    private val username: String
    private val password: String

    init {
        host = propertyController.database_host()
        port = propertyController.database_port()
        database = propertyController.database_name()
        username = propertyController.database_username()
        password = propertyController.database_password()
    }

    /**
     * Connect to the database.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    fun connect(): Boolean {
        db = Database.connect(
                url = "jdbc:postgresql://$host:$port/$database",
                driver = "org.postgresql.Driver",
                user = username,
                password = password
        )

        try {
            transaction {
                Sensors.select { Sensors.name eq "sensor_name" }.count()
            }
        } catch (e: Exception) {
            val alert = Alert(Alert.AlertType.ERROR)

            var header: String = ""
            var message: String = ""

            println("Cause: ${e.cause.toString()}")

            // Database is not running!
            if (e.cause is ConnectException) {
                header = "Connection refused"
                message = "Connection to the host at $host:$port was refused!"
                println(e.message)
            }

            else  {
                header = "An error occured during connecting to the database. See the message below for a detailed description"

                val original = (e as? ExposedSQLException)?.cause
                when (original) {
                    is SQLIntegrityConstraintViolationException ->
                        println("SQL constraint violated")
                    is BatchUpdateException ->
                        println("SQL constraint violated")
                    is PSQLException ->
                        println("Test")
                    else -> {
                        message = e.message.toString()
                    }
                }
            }


            alert.title = "Database error"
            alert.headerText = header
            alert.contentText = message

            val okButton = ButtonType("Exit", ButtonBar.ButtonData.APPLY)
            alert.buttonTypes.setAll(okButton)

            alert.showAndWait()

            return false
        }

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
            addLogger(StdOutSqlLogger)

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
