package BottomupDDD.web.view

import BottomupDDD.domain.user.*

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

typealias UserDeleteViewModel = User

class UserDeleteTemplate(val user: UserDeleteViewModel) : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                h2 { +"Delete" }
                div(classes = "row") {
                    p(classes = "col-md-3") { +"ID" }
                    p(classes = "col-md-3") { +user.id.value }
                }
                div(classes = "row") {
                    p(classes = "col-md-3") { +"User name" }
                    p(classes = "col-md-3") { +user.userName.value }
                }
                div(classes = "row") {
                    p(classes = "col-md-3") { +"First name" }
                    p(classes = "col-md-3") { +user.name.firstName }
                }
                div(classes = "row") {
                    p(classes = "col-md-3") { +"Family name" }
                    p(classes = "col-md-3") { +user.name.familyName }
                }
                form {
                    action = "/user/deleteuser"; method = FormMethod.post
                    hiddenInput { name = "id"; value = user.id.value }
                    submitInput(classes = "btn btn-primary") { value = "Confirm" }
                }
                a("/user") {
                    classes = setOf("btn", "btn-secondary"); role = "button"
                    +"Back"
                }
            }
        }
    }
}
