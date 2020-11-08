package martinmoser.controllers

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller

class StatusController: Controller() {
    private val statusText = SimpleStringProperty("Status: ")

    /**
     * Set a new status.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     */
    fun setStatus(status: Status) {
        statusText.set("Status: ${status.text}")
    }

    /**
     * Get the current status.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @returns The status message.
     */
    fun getStatus(): SimpleStringProperty  { return statusText }
}