package martinmoser.views

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import martinmoser.SerialDevice
import martinmoser.SerialDeviceManager
import martinmoser.controllers.MainController
import martinmoser.controllers.MessageController
import martinmoser.controllers.StatusController
import martinmoser.models.Sensor
import martinmoser.models.Status
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
    val mainController: MainController by inject()
    val messageController: MessageController by inject()
    private val statusController: StatusController by inject()

    init {
        statusController.setStatus(Status.DISCONNECTED)

        // create a daemon thread
        val timer = Timer("schedule", true);

        // schedule at a fixed rate
        timer.scheduleAtFixedRate(1000, 1000) {
            val sensor1 = mainController.sensors[0]
            sensor1.updateValue((Random.nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor2 = mainController.sensors[1]
            sensor2.updateValue((Random.nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()

            // -----------------------------------

            val sensor3 = mainController.sensors[2]
            sensor3.updateValue((Random.nextFloat() * 50 - 25))

            mainController.model.commit()
            mainController.refresh()
        }
    }

    override fun onDock() {
        currentStage?.setOnCloseRequest { evt ->
            var x = 9

            val alert = Alert(AlertType.CONFIRMATION)
            alert.title = "Confirmation Dialog"
            alert.headerText = "Look, a Confirmation Dialog"
            alert.contentText = "Are you ok with this?"

            val okButton = ButtonType("Yes", ButtonBar.ButtonData.YES)
            val noButton = ButtonType("No", ButtonBar.ButtonData.NO)

            alert.buttonTypes.setAll(okButton, noButton)

            val result = alert.showAndWait()
            if (result.get() == okButton) {
               x = 8
            } else {
                x = 10
                evt.consume()
            }

            /*println("X: $x")

            if (x == 10) {
                tt.consume()

                println("Here")
            } else {
                println("Closing!")
            }*/
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
                    item("Item 2")

                    item("Connecto to Arduino", "Shortcut + C").action {
                        val serialDeviceManager = SerialDeviceManager()
                        if (serialDeviceManager.searchArduino(scan = true)) {
                            // SearchArduino will return false if the targetPort is null.
                            // So no null safe call is required
                            val serialDevice = SerialDevice(serialDeviceManager.targetPort!!)
                            serialDevice.connect()
                        }
                    }
                }
            }
        }

        left {
            listview<String> {
                items.add("Temp Sensor")
                items.add("Sensor 1")
                items.add("Sensor 2")

                contextmenu {
                    item("Details")
                }
            }
        }

        center {
            tableview<Sensor>(mainController.sensors) {
                column("Sensor", Sensor::name)
                column("Value Type", Sensor::value_type)
                column("Unit", Sensor::unit)

                column("Value", Sensor::value).cellFormat {
                    text = "%.${2}f".format(it)
                }

                column("Last updated", Sensor::last_updated).cellFormat {
                    text = "${it.hour}:" + "%02d".format(it.minute) + ":%02d".format(it.second)
                }

                mainController.model.rebindOnChange(this) { selectedSensor -> item = selectedSensor ?: Sensor()
                    mainController.model.commit()
                }
            }
        }

        bottom {
            vbox {
                textarea(messageController.getMessages())
                label(statusController.getStatus())
            }
        }
    }
}
