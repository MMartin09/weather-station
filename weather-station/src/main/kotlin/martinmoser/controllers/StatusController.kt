package martinmoser.controllers

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.MenuItem
import martinmoser.models.Status
import tornadofx.Controller
import tornadofx.ViewModel

/**
 * Controller for the connection status.
 *
 * The controller is used to set the status message in the main window.
 * At initialization the status is set to disconnected.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class StatusController: Controller() {
    private var status: Status = Status.DISCONNECTED
    private val statusText = SimpleStringProperty("Status: ")

    init {
        setStatus(Status.DISCONNECTED)
    }

    /**
     * Set a new status.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     */
    fun setStatus(status: Status) {
        this.status = status
        statusText.set("Status: ${status.text}")
    }

    /**
     * Get the current status.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @returns The status.
     */
    fun getStatus(): Status = status

    /**
     * Get the current status text.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @returns The status message.
     */
    fun getStatusText(): SimpleStringProperty = statusText
}