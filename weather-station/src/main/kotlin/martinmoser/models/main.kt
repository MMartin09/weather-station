package martinmoser.models

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import martinmoser.models.Sensor
import martinmoser.models.SensorModel
import tornadofx.*
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane

fun main(args: Array<String>) {
    println("Hello World!")
/*
    val tempSensor = Sensor("Temp Sensor", martinmoser.models.ValueType.FLOAT, "°C")

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")

    tempSensor.value = 23.3F

    println("Tmperature sensor: Value = ${tempSensor.value}; last_updated = ${tempSensor.last_updated}")
*/
    launch<MainApp>(args)
}

class MainApp: App(MainView::class)

class Person(name: String? = null, title: String? = null) {
    //val nameProperty = SimpleStringProperty(this, "name", name)
    //var name by nameProperty

    var name: String? = name

    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty
}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val name = bind { Person::name.toProperty() }
    val title = bind(Person::titleProperty)
}

class PersonController() : Controller() {
    val model = PersonModel(Person())
}

class MainView: View() {
    override val root = BorderPane()
    val persons = listOf(Person("John", "Manager"), Person("Jay", "Worker bee")).observable()
    //val model = PersonModel(Person())

    val controller: PersonController by inject()

    init {
        with(root) {
            center {
                tableview(persons) {
                    column("Name", Person::name)
                    column("Title", Person::titleProperty)

                    // Update the person inside the view model on selection change
                    controller.model.rebindOnChange(this) { selectedPerson ->
                        item = selectedPerson ?: Person()
                    }
                }
            }

            right {
                form {
                    fieldset("Edit person") {
                        field("Name") {
                            textfield(controller.model.name)
                        }
                        field("Title") {
                            textfield(controller.model.title)
                        }
                        button("Save") {
                            enableWhen(controller.model.dirty)
                            action {
                                save()
                            }
                        }
                        button("Reset").action {
                            controller.model.rollback()
                        }
                    }
                }
            }
        }
    }

    private fun save() {
        // Flush changes from the text fields into the model
        controller.model.commit()

        // The edited person is contained in the model
        val person = controller.model.item

        // A real application would persist the person here
        println("Saving ${person.name} / ${person.title}")
    }
}

class MainController: Controller() {
    val sensors = FXCollections.observableArrayList<Sensor>()
    val selectedSensor = SensorModel()

    init {
        sensors.add(Sensor("Temp Sensor", 0, "°C"))
    }
}