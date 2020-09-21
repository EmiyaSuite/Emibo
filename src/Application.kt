package rs.ij

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.sessions.*
import kotlinx.css.properties.TextDecoration
import rs.ij.DownloadManager.generateRandomString
import rs.ij.DownloadSession
import rs.ij.IPSession.checkNotExceed
import java.net.URI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    VillagersData.ping()
    DownloadManager.ping()
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ForwardedHeaderSupport) // WARNING: for security, do not include this if not behind a reverse proxy
    install(XForwardedHeaderSupport) // WARNING: for security, do not include this if not behind a reverse proxy

    install(HSTS) {
        includeSubDomains = true
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound, HttpStatusCode.BadRequest, HttpStatusCode.InternalServerError) {
            call.respondRedirect("/")
        }
    }

    install(Sessions) {
        cookie<DownloadSession>(generateRandomString(6))
    }

    routing {
        get("/") {
            call.respondHtmlTemplate(Contents(data = listOf(VillagersData.amibo, VillagersData.welcome, VillagersData.figure))) { }
        }

        get("/category") {
            call.respondRedirect("/")
        }

        get("/category/{link}") {
            val link: Int? = call.parameters["link"]?.toIntOrNull()
            if (link != null) {
                when (link) {
                    in 1..4 -> {
                        val multiplied = link * 100
                        val filtered = VillagersData.amibo.filter {
                            it.number?.let {
                                number -> number in (multiplied-100) + 1..multiplied
                            } ?: false
                        }
                        call.respondHtmlTemplate(Contents(data = listOf(filtered))) { }
                    }
                    5 -> {
                        call.respondHtmlTemplate(Contents(data = listOf(VillagersData.welcome, VillagersData.figure))) { }
                    }
                    else -> call.respondRedirect("/")
                }
            } else {
                call.respondRedirect("/")
            }
        }

        get("/menu") {
            call.respondHtmlTemplate(Layout()) {
                content {
                    val ranking = DownloadManager.getRanking().sortedByDescending { it.count }
                    link(href = "https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css", rel = "stylesheet") {
                        integrity = "sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
                        attributes["crossOrigin"] = "anonymous"
                        div("container-fluid") {
                            h3 {
                                +"Villagers ranking!"
                            }
                            table("table") {
                                thead {
                                    tr {
                                        th(scope = ThScope.col) { +"#" }
                                        //th(scope = ThScope.col) { +"English name" }
                                        th(scope = ThScope.col) { +"Name" }
                                        th(scope = ThScope.col) { +"Downloads" }
                                    }
                                }
                                tbody {
                                    ranking.forEachIndexed { index, item ->
                                        tr {
                                            td {
                                                style { width = LinearDimension("25%") }
                                                img(src="/static/cards/${item.number}.jpg") {
                                                    style { width = LinearDimension("100%") }
                                                }
                                            }
                                            td {
                                                +"${index + 1}위 ${item.koreanName}"
                                            }
                                            td {
                                                +item.count.toString()
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

        get("/data/{key}") {
            val key = call.parameters["key"]
            val session = call.sessions.get<DownloadSession>() ?: DownloadSession()
            if (key != null) {
                val entry = DownloadManager.keys[key]
                val sessionCount = session.downloadHistory[key] ?: 0
                entry?.let {
                    val clientIp = call.request.origin.host
                    val userAgent = call.request.userAgent() ?: ""
                    val stream = javaClass.getResourceAsStream("/${entry.figure?.let { "Figures" } ?: "Cards"}/${entry.file}" )
                    if (stream != null) {
                        val uri = URI(null, null, "${entry.name}.bin", null)
                        call.response.header("Content-Disposition", "attachment; filename=\"${uri.toASCIIString()}\"")
                        if (!(entry.special == true || entry.figure == true) && sessionCount < 1 && checkNotExceed(clientIp)) {
                            session.downloadHistory[key] = sessionCount + 1
                            IPSession.addCount(clientIp)
                            call.sessions.set(session)
                            DownloadManager.setRanking(entry.name)
                        }
                        call.respondBytes(stream.readBytes(), contentType = ContentType.parse("application/octet-stream"))
                    }
                }

            }
        }

        get("/emibo.css") {
            call.respondCss {
                a {
                    textDecoration = TextDecoration.none
                    color = Color.black

                }
                rule("a:hover") {
                    color = Color.black
                    textDecoration = TextDecoration.none
                }
                rule(".card-body > div") {
                    display = Display.inline
                }
            }
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }
    }
}

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
