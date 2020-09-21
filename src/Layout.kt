package rs.ij

import io.ktor.html.*
import kotlinx.html.*

/**
 * Layout of HTML
 */
class Layout : Template<HTML> {
    val content = Placeholder<FlowContent>()
    override fun HTML.apply() {
        head {
            meta(name = "viewport", content = "width=device-width, user-scalable=no")
            title { +"Emibo - Unofficial Animal Crossing Villager Database" }
            link(href = "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css", rel = "stylesheet") {
                integrity = "sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
                attributes["crossOrigin"] = "anonymous"
            }
            script(src = "https://code.jquery.com/jquery-3.4.1.min.js") { }
            script(type = "text/javascript", src = "https://cdnjs.cloudflare.com/ajax/libs/jquery.lazy/1.7.11/jquery.lazy.min.js") { }
            link(rel = "stylesheet", href = "/emibo.css")
        }
        body {
            header {
                nav("navbar navbar-expand navbar-light bg-light") {
                    a(classes="navbar-brand", href="/") {
                        +"Emibo DB"
                    }
                    form {
                        input(classes = "form-control mr-sm-7 my-2 my-lg-0", type = InputType.search) {
                            placeholder = "Search"
                        }
                    }
                }
            }
            div {
                id = "content"
                div("container-fluid") {
                    insert(content)
                }
            }
        }

    }
}