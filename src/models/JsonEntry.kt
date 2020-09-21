package rs.ij.models

/**
 * Entry item
 *
 * @property englishName English name parse from name.
 * @property koreanName Korean name parse from name_kor.
 * @property number Card number of 0 ~ 400. Welcome, Figure, Sanrio have value as null.
 * @property file Path of binary file.
 * @property customNumber Custom number of Welcome(w), figure(f), Sanrio(s)
 * @property series Series about card. 0 ~ 400 will be in number, others will be in string.
 * @property type welcome | figure
 * @property personality Personality in [English, Korean]
 */
data class JsonEntry (
    val englishName: String,
    val koreanName: String,
    val number: Int? = null,
    var file: String,
    val customNumber: String? = null,
    val series: Int? = null,
    val type: String? = null,
    val personality: ArrayList<String>?
)