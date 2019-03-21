package gui.widget

import javafx.event.EventTarget
import javafx.scene.control.Button
import tornadofx.*

/**
 * Dugme koje ima podršku za dugotrajne zadatke, za koje je potrebno da se izvršavaju na zasebnoj niti.
 * Tokom izvršavanja, pojavljuje se [ProgressIndicator][javafx.scene.control.ProgressIndicator] u okviru dugmeta.
 * Tokom izvršavanja zadatka, dugme je onesposobljeno (klikovi na dugme nemaju efekta)
 */
class AsyncButton(title: String?) : Button(title) {

    private var taskIsRunning = false

    /**
     * Ova metoda se izvršava na niti pozivanja, neposredno pre nego što krene izvršavanje na zasebnoj niti.
     * Korisno za, na primer, menjanje stila dugmeta, ili sličnog reagovanja grafičkog interfejsa.
     */
    var beforeAction: AsyncButton.() -> Unit = {}

    /**
     * Ova metoda se izvršava na zasebnoj niti.
     */
    private var onActionAsync: AsyncButton.() -> Unit = {}

    /**
     * Ova metoda se izvršava na niti pozivanja, a neposredno nakon što se završi dugotrajni zadatak.
     */
    var afterAction: AsyncButton.() -> Unit = {}

    init {
        setOnAction {
            if (!taskIsRunning) {
                taskIsRunning = true
                beforeAction()

                runAsyncWithProgress {
                    onActionAsync()
                } ui {
                    afterAction()
                } finally {
                    taskIsRunning = false
                }

            }
        }
    }
}

/**
 * DSL metoda u skladu sa ostalim TornadoFX metodama.
 */
fun EventTarget.asyncbutton(title: String?, op: (AsyncButton.() -> Unit) = {}): AsyncButton {
    val button = AsyncButton(title)
    button.op()
    this.add(button)
    return button
}