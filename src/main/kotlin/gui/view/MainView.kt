package gui.view

import data.Repository
import gui.view.welcome.WelcomeScreenWizard
import gui.widget.TimetableViewWrapper
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.ButtonBar
import javafx.stage.FileChooser
import tornadofx.*
import javax.imageio.ImageIO
import java.awt.image.BufferedImage



class MainView: View("MatfAsistent") {

    override val root = vbox {
        val timetableView = TimetableViewWrapper(Repository.bestTimetableProperty)
        add(timetableView)

        buttonbar {

            spacing = 10.0

            paddingAll = 10

            button("Napravi novi raspored", type = ButtonBar.ButtonData.OTHER).setOnAction {
                Repository.bestTimetable = null
                find<WelcomeScreenWizard>().openModal()
            }



            button("Pretvori u sliku", type = ButtonBar.ButtonData.OTHER).setOnAction {

                val image = timetableView.snapshot(null, null)

                val fileChooser = FileChooser()
                fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG slika", "*.png"))

                val file = fileChooser.showSaveDialog(primaryStage)

                if (file != null) {
                    // Kod preuzet sa https://stackoverflow.com/a/48128599/1640235
                    val bufferedImage = SwingFXUtils.fromFXImage(image, null)
                    val imageRGB = BufferedImage(bufferedImage.width, bufferedImage.height, BufferedImage.OPAQUE)
                    val graphics = imageRGB.createGraphics()
                    graphics.drawImage(bufferedImage, 0, 0, null)
                    ImageIO.write(imageRGB, "png", file)
                }
            }
        }
    }
}