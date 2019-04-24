package BottomupDDD.web.view

import BottomupDDD.domain.user.*

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

typealias UserDetailViewModel = User

class UserDetailTemplate(val user: UserDetailViewModel) : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                h2 { +"Detail" }
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
                a("/user/edit/${user.id.value}") {
                    classes = setOf("btn", "btn-primary"); role = "button"
                    +"Edit"
                }
                a("/user") {
                    classes = setOf("btn", "btn-secondary"); role = "button"
                    +"Back"
                }
            }
        }
    }
}
