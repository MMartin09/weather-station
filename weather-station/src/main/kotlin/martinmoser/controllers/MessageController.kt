package martinmoser.controllers

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import java.time.LocalTime


/**
 * Controller for the message window.
 *
 * @author MMartin09
 * @since 0.1.0
 */
class MessageController: Controller() {
    private val message = SimpleStringProperty("")
    private val messageArray = mutableListOf<String>()

    private val maxNumberOfLines = 5

    /**
     * Add a new message.
     *
     * Add a message to the message box.
     * Each new message is printed into a new line.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param msg Message that should be printed
     */
    fun addMessage(msg: String) {
        if (messageArray.size >= maxNumberOfLines) deleteLine()

        messageArray.prepend(LocalTime.now().toString() + ": " + msg + "\n")
        val tmp = messageArray.toList().toString().replace("[", "").replace("]", "").replace(", ", "")

        message.set(tmp)
    }

    /**
     * Get all messages.
     *
     * @returns The messages for the message box.
     */
    fun getMessages(): SimpleStringProperty { return message }

    /**
     * Delete the oldest message.
     *
     * Remove the oldest message from the messages array.
     * Since new messages are added to the front, the oldest message is the end of the list.
     *
     * @author MMartin09
     * @since 0.1.0
     */
    private fun deleteLine() {
        messageArray.removeLast()
    }

    /**
     * Add new item at the front of the message list.
     *
     * Adds a new item at the front of the message list.
     *
     * @author MMartin09
     * @since 0.1.0
     *
     * @param element Element that should be added
     */
    private fun MutableList<String>.prepend(element: String) {
        add(0, element)
    }
}