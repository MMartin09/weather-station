package martinmoser.controllers

import org.jetbrains.exposed.sql.Database
import tornadofx.Controller

class DatabaseController : Controller() {
    private var db: Database? = null

    /**
     * Connect to the database.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    fun connect() : Boolean {
        db = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")

        if (db == null) return false
        return true
    }
}