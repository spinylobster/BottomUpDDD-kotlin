package BottomupDDD.web.view

import BottomupDDD.domain.user.*

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

typealias UserEditViewModel = User

class UserEditTemplate(val user: UserEditViewModel) : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                h2 { +"Edit" }
                form {
                    action = "/user/update"; method = FormMethod.post
                    div(classes = "form-group") {
                        label { attributes["for"] = "username"; +"User name" }
                        textInput(classes = "form-control") {
                            id = "username"; name = "username"
                            value = user.userName.value
                        }
                    }
                    div(classes = "form-group") {
                        label { attributes["for"] = "firstname"; +"First name" }
                        textInput(classes = "form-control") {
                            id = "firstname"; name = "firstname"
                            value = user.name.firstName
                        }
                    }
                    div(classes = "form-group") {
                        label { attributes["for"] = "familyname"; +"Family name" }
                        textInput(classes = "form-control") {
                            id = "familyname"; name = "familyname"
                            value = user.name.familyName
                        }
                    }
                    hiddenInput { name = "id"; value = user.id.value }
                    submitInput(classes = "btn btn-primary") { value = "Confirm" }
                }
            }
        }
    }
}
