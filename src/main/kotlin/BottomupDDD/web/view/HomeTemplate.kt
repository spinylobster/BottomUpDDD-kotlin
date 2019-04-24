package BottomupDDD.web.view

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

class HomeTemplate : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                a("/user") {
                    classes = setOf("btn", "btn-primary"); role = "button"
                    +"Try a DEMO"
                }
            }
        }
    }
}
