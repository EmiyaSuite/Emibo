package rs.ij

import io.ktor.html.*
import kotlinx.css.Color
import kotlinx.css.FontWeight
import kotlinx.css.LinearDimension
import kotlinx.html.*
import rs.ij.models.JsonEntry

/**
 * Contents area of HTML
 *
 * @property data List of villagers
 */
class Contents(private val layout: Layout = Layout(), val data: List<List<JsonEntry>> = listOf()) : Template<HTML> {
    private val links = listOf(
            Pair("", "ALL"),
            Pair("1", "~100"),
            Pair("2", "101~200"),
            Pair("3", "201~300"),
            Pair("4", "301~400"),
            Pair("5", "Welcome/Sanrio/Promo")
    )

    override fun HTML.apply() {
        insert(layout) {
            content {
                link(rel="stylesheet", href = "https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css") {
                    integrity = "sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
                    attributes["crossOrigin"] = "anonymous"
                }
                script(src = "/static/emibo.js") {}
                div("btn-group btn-group-sm flex-wrap") {
                    role = "group"
                    for (link in links) {
                        a(classes = "btn btn-secondary", href="/category/${link.first}") {
                            type = "button"
                            +link.second
                        }
                    }
                }
                div("row") {
                    for (series in data) {
                        for (entry in series) {
                            val number = entry.number ?: entry.customNumber
                            a(classes = "card col-2", href = "/data/${entry.file}") {
                                img(classes = "card-img-top", alt = entry.englishName) {
                                    attributes["data-src"] = "/static/cards/$number.${if (entry.number == null) "png" else "jpg"}"
                                }
                                div("card-body") {
                                    div {
                                        div {
                                            style { fontWeight = FontWeight.bold }
                                            +number.toString()
                                        }
                                        div {
                                            id = "name"
                                            +"${entry.englishName} ${entry.koreanName}"
                                        }
                                        div {
                                            style { fontSize = LinearDimension("12px"); color = Color.blue }
                                            if (entry.personality?.let { it[0] == "" } != false) {
                                                +"특수 주민 (Special)"
                                            } else {
                                                +"${entry.personality[1]} (${entry.personality[0]})"
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
}