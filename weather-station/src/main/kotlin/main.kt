
import models.TempSensor
import tornadofx.*

fun main(args: Array<String>) {
    println("Hello World!")

    val tempSensor = TempSensor("Temp Sensor", ValueType.FLOAT, "°C")

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")

    tempSensor.value = 23.3F

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")

    launch<MainApp>(args)
}

class MainApp: App(MainView::class)

class MainView: View() {
    override val root = vbox {
        button("Press me")
        label("Waiting")
    }
}