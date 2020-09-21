package rs.ij.models

/**
 * Generated download key object
 *
 * @property name {Number} - {English name} - {Korean name} use as identifier
 * @property file Path of binary file.
 * @property special Is villager is special villager.
 * @property figure Is file is figure dump file.
 */
data class DownloadKey (
    val name: String,
    val file: String,
    val special: Boolean? = null,
    val figure: Boolean?= null
)