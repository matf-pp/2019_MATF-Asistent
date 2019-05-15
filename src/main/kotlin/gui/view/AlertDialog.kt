package gui.view

import javafx.scene.control.ButtonBar
import tornadofx.*

/** Dijalog koji prikazuje upozorenje na ekranu.
 *
 * Ova klasa postoji zato što [javafx.scene.control.Alert] klasa ne radi ispravno sa velikom količinom teksta.
 *
 */
class AlertDialog(title: String = "Upozorenje", private val content: String)  : View(title) {

    override val root = vbox(10) {

        maxWidth = 400.0
        paddingAll = 10

        label(title).style {
            wrapText = true
            fontSize = 20.px
        }
        label(content).style {
            paddingAll = 20
            wrapText = true
            fontSize = 14.px
        }
        buttonbar {
            button("U redu", ButtonBar.ButtonData.OTHER).setOnAction {
                this@AlertDialog.close()
            }
        }
    }
}

fun showAlertDialog(title: String, content: String) {
    AlertDialog(title, content).openModal()
}