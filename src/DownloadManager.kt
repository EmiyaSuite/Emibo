package rs.ij

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import rs.ij.models.DownloadKey
import rs.ij.models.JsonEntry
import rs.ij.models.RankingItem
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Generated download keys, ranking handler.
 */
object DownloadManager {
    val keys = init()
    var updateRankingRequired: Boolean = true
    var rankingData: List<RankingItem> = listOf()

    /**
     * Generate download keys, change file value to generated key.
     *
     * @return Map, randomly generated download key as Key, DownloadKey object as Value.
     */
    private fun init(): Map<String, DownloadKey> {
        val key = mutableMapOf<String, DownloadKey>()
        for ((index, entry) in VillagersData.amibo.withIndex()) {
            val number = entry.number
            number.takeIf { it != -1 }?.let {
                val generatedKey = generateRandomString(8)
                val isSpecial = entry.personality?.let { it[0] == "" } ?: true
                key[generatedKey] = DownloadKey(file = entry.file, name = getName(entry), special = isSpecial)
                VillagersData.amibo[index].file = generatedKey
            }
        }
        for ((index, entry) in VillagersData.welcome.withIndex()) {
            val generatedKey = generateRandomString(12)
            key[generatedKey] = DownloadKey(file = entry.file, name = getName(entry))
            VillagersData.welcome[index].file = generatedKey
        }
        for ((index, entry) in VillagersData.figure.withIndex()) {
            val generatedKey = generateRandomString(16)
            key[generatedKey] = DownloadKey(file = entry.file, name = getName(entry), figure = true)
            VillagersData.figure[index].file = generatedKey
        }
        return key
    }

    /**
     * Set download count to ranking file using villagers identifier name.
     *
     * @param name {Number} - {English name} - {Korean name}
     */
    fun setRanking(name: String) {
        val file = File("ranking.json")
        val created = file.createNewFile()
        var obj: MutableMap<String, Int>? = if (created) mutableMapOf() else Klaxon().parse(file)
        if (obj != null) {
            val value = obj[name]
            if (value == null) {
                obj[name] = 1
            } else {
                obj[name] = value + 1
            }
        } else {
            obj = mutableMapOf(Pair(name, 1))
        }
        updateRankingRequired = true
        file.writeText(Klaxon().toJsonString(obj))
    }

    /**
     * Get all ranking list of whole villagers.
     *
     * @return List of ranking item.
     */
    fun getRanking(): List<RankingItem> {
        if (updateRankingRequired) {
            val file = File("ranking.json")
            if (file.exists()) {
                val dataList = mutableListOf<RankingItem>()
                val rankingMap = Klaxon().parse<Map<String, Int>>(file) ?: return listOf()
                for (item in rankingMap.iterator()) {
                    val split = item.key.split("-")
                    dataList.add(RankingItem(split[0].trim(), split[1].trim(), split[2].trim(), item.value))
                }
                rankingData = dataList
                return dataList
            } else {
                return listOf()
            }
        } else {
            return rankingData
        }

    }

    /**
     * Trigger object to run init.
     */
    fun ping() {
        return
    }

    /**
     * Generate random string for a new token string.
     *
     * @param length Length of random string. Default: 64
     * @return Randomly generated string.
     */
    fun generateRandomString(length: Int = 64): String {
        val chars = ('A'..'Z') + ('a'..'z') + (0..9)
        return (1..length).map { chars.random() }.joinToString("")
    }

    /**
     * Generate filename from JsonEntry object.
     *
     * @param entry JsonEntry for data
     * @return name (Number - English name - Korean name)
     */
    private fun getName(entry: JsonEntry): String {
        val number: String? = entry.number?.toString() ?: entry.customNumber
        return "${number ?: "X"} - ${entry.englishName} - ${entry.koreanName}"
    }
}