package rs.ij.models

/**
 * Defined for ranking lists
 *
 * @property number Number of villager
 * @property englishName English name of villager.
 * @property koreanName Korean name of villager.
 * @property count Downloaded count of villager.
 */
data class RankingItem (
        val number: String,
        val englishName: String,
        val koreanName: String,
        val count: Int
)