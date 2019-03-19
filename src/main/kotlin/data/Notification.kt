package data

import javafx.scene.image.Image

// Ako je potrebno nasleđivanje, promeniti modifier "data" u "open"
data class Notification(var title: String, var content: String, var image: Image? = null) {

    override fun toString(): String {
        return "$title: $content"
    }

}