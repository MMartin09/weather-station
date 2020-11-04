
import models.TempSensor
import tornadofx.*
import java.time.LocalDateTime

fun main(args: Array<String>) {
    println("Hello World!")

    val tempSensor = TempSensor("Temp Sensor", ValueType.FLOAT, "°C")

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")

    tempSensor.value = 23.3F

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")
}