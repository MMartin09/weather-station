package martinmoser.views

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import martinmoser.controllers.MainController
import martinmoser.controllers.MessageController
import martinmoser.controllers.SerialDeviceController
import martinmoser.controllers.StatusController
import martinmoser.models.Sensor
import martinmoser.models.Status
import martinmoser.views.dialogs.SensorDetailsDialog
import tornadofx.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random


/**
 * Main view of the application
 *
 * @author MMartin09
 * @since 0.1.0
 */
class MainView: View() {
    private val mainController: MainController by inject()
    private val serialDeviceController: SerialDeviceController by inject()
    private val messageController: MessageController by inject()
    private val statusController: StatusController by inject()

    init {
        // create a daemon thread
        val timer = Timer("schedule", true);

        // schedule at a fixed rate
        timer.scheduleAtFixedRate(1000, 1000) {
            val sensor1 = mainController.sensors[0]
            sensor1.updateValue((Random.nextFloat() * 50 - 25))

            //mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor2 = mainController.sensors[1]
            sensor2.updateValue((Random.nextFloat() * 50 - 25))

            //mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor3 = mainController.sensors[2]
            sensor3.updateValue((Random.nextFloat() * 50 - 25))

            //mainController.model.commit()
            mainController.refresh()
        }
    }

    override fun onDock() {
        currentStage?.setOnCloseRequest { evt ->
            // Display the confirmation dialog only if an Arduino is still connected
            if (statusController.getStatus() == Status.CONNECTED) {
                val alert = Alert(AlertType.CONFIRMATION)
                alert.title = "Confirmation Dialog"
                alert.headerText = "Look, a Confirmation Dialog"
                alert.contentText = "Are you ok with this?"

                val okButton = ButtonType("Yes", ButtonBar.ButtonData.YES)
                val noButton = ButtonType("No", ButtonBar.ButtonData.NO)

                alert.buttonTypes.setAll(okButton, noButton)

                val result = alert.showAndWait()
                if (result.get() == noButton) {
                    evt.consume()
                }

                // If yes is clicked, discconect from the Arduino
                else {
                    if (!serialDeviceController.disconnect())
                        evt.consume()
                }
            }
        }
    }


    override val root = borderpane  {
        top {
            menubar {
                menu("File") {
                    item("Item 1")
                    item("Item 2")
                }

                menu("Edit") {
                    item("Item 1")
                    item("Item 2").action {

                    }

                    item("Connect to Arduino", "Shortcut + C").action {
                        if (statusController.getStatus() == Status.CONNECTED) {
                            if (serialDeviceController.disconnect()) {
                                this.items.forEach{
                                    if (it.text == "Disconnect from Arduino") {
                                        it.text = "Connect to Arduino"
                                    }
                                }
                            }
                        }

                        else {
                            if (serialDeviceController.connect()) {
                                this.items.forEach{
                                    if (it.text == "Connect to Arduino") {
                                        it.text = "Disconnect from Arduino"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        left {
            listview<String>(mainController.sensorNames()) {
                // If a new item is selected update the model to hold the new selected item.
                mainController.model.rebindOnChange(this) {
                    selectedSensor -> item = mainController.getSensorByName(selectedSensor!!) ?: Sensor()
                }

                // Conextmenu fpr the sensors. Opens with a right click on a sensor name.
                contextmenu {
                    item("Details").action {
                        openInternalWindow<SensorDetailsDialog>()
                    }
                }

                onUserSelect(2) { println("Test") }
            }
        }

        center {
            tableview<Sensor>(mainController.sensors) {
                column("Sensor", Sensor::name)
                column("Unit", Sensor::unit)

                column("Value", Sensor::value).cellFormat {
                    text = "%.${2}f".format(it)
                }

                column("Last updated", Sensor::last_updated).cellFormat {
                    text = "${it.hour}:" + "%02d".format(it.minute) + ":%02d".format(it.second)
                }
            }
        }

        bottom {
            vbox {
                textarea(messageController.getMessages())
                label(statusController.getStatusText())
            }
        }
    }
}
