package BottomupDDD.web.view

import BottomupDDD.domain.user.*

import io.ktor.html.*
import kotlinx.html.*
import kotlinx.html.dom.*

typealias UserIndexViewModel = Iterable<User>

class UserIndexTemplate(val users: UserIndexViewModel) : Template<HTML> {
    val root = BootstrapTemplate()
    override fun HTML.apply() {
        insert(root) {
            content {
                h2 { +"Index" }
                div(classes = "row") {
                    a("/user/newuser") {
                        classes = setOf("btn", "btn-primary"); role = "button"
                        +"New User"
                    }
                }
                div(classes = "row") {
                    table(classes = "table") {
                        thead {
                            tr {
                                th { +"ID" }
                                th { +"UserName" }
                                th {}; th {} // for "Detail", "Delete" buttons
                            }
                        }
                        tbody {
                            users.forEach {
                                tr {
                                    td { +it.id.value }
                                    td { +it.userName.value }
                                    td {
                                        a("/user/detail/${it.id.value}") {
                                            classes = setOf("btn", "btn-secondary"); role = "button"
                                            +"Detail"
                                        }
                                    }
                                    td {
                                        a("/user/delete/${it.id.value}") {
                                            classes = setOf("btn", "btn-danger"); role = "button"
                                            +"Delete"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
