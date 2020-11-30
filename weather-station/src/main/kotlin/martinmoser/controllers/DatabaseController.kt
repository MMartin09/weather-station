package martinmoser.controllers

import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller

object Sensors : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Sensors_ID")
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
        db = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")

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

            commit()
        }
    }

    fun add_sensors(sensors: ObservableList<String>) {
        transaction {
            sensors.forEach {
                SchemaUtils.create(Sensors)

                val sens_name = it

                Sensors.insert {
                    it[name] = sens_name
                }

                for (sensor in Sensors.selectAll()) {
                    println("${sensor[Sensors.id]}: ${sensor[Sensors.name]}")
                }
            }
        }
    }
}
