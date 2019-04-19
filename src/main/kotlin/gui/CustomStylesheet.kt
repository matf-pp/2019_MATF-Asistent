package gui

import tornadofx.Stylesheet

class CustomStylesheet : Stylesheet() {

    init {
        with (Stylesheet) {
            root {
                //backgroundColor = multi(c("#6A6A6A"))
            }
            label {
                //textFill = Color.LIGHTGRAY
            }
            button {
                //borderColor += box(topColor, rightColor, leftColor, bottomColor)
//                backgroundRadius = multi(box(0.px))
//                backgroundColor = multi(Color.MIDNIGHTBLUE)
//                textFill = Color.LIGHTGRAY
            }
        }
    }
}