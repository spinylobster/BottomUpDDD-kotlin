package BottomupDDD.web.view

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

class NewUserTemplate : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                h2 { +"New User" }
                form {
                    action = "/user/register"; method = FormMethod.post
                    div(classes = "form-group") {
                        label { attributes["for"] = "username"; +"User name" }
                        textInput(classes = "form-control") { id = "username"; name = "username" }
                    }
                    div(classes = "form-group") {
                        label { attributes["for"] = "firstname"; +"First name" }
                        textInput(classes = "form-control") { id = "firstname"; name = "firstname" }
                    }
                    div(classes = "form-group") {
                        label { attributes["for"] = "familyname"; +"Family name" }
                        textInput(classes = "form-control") { id = "familyname"; name = "familyname" }
                    }
                    submitInput(classes = "btn btn-primary") { value = "Confirm" }
                }
            }
        }
    }
}
