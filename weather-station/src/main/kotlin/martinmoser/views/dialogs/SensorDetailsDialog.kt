package martinmoser.views.dialogs

import javafx.scene.Parent
import tornadofx.View
import tornadofx.hbox
import tornadofx.label

class SensorDetailsDialog: View() {

    override val root = hbox {
        label("Sensor details")
    }
}