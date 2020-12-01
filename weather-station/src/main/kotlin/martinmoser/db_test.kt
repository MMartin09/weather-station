package martinmoser

import martinmoser.controllers.DatabaseController
import tornadofx.find

fun main(args: Array<String>) {
    val dbController = find(DatabaseController::class)

    if (dbController.connect()) {
        println("Connected to the database!")

        dbController.create_schema()
    } else
        println("Could not connect to the database!")
}
