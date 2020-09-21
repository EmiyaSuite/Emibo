package rs.ij

import com.beust.klaxon.Klaxon
import rs.ij.models.Json
import rs.ij.models.JsonEntry

/**
 * Villagers data from JSON file handler
 */
object VillagersData {
    val amibo = load("amibo.json")
    val figure = load("figure.json")
    val welcome = load("welcome.json")

    /**
     * Load villagers json file.
     *
     * @param filename Filename of villagers JSON file.
     * @return List of villager json entries.
     */
    fun load(filename: String): List<JsonEntry> {
        val stream = javaClass.getResourceAsStream("/$filename")
        val list = Klaxon().parse<Json>(stream)
        return list?.data ?: listOf()
    }

    /**
     * Trigger object to run init.
     */
    fun ping() {
        return
    }
}