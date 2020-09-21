package rs.ij

/**
 * Check the download count by IP Addresses
 *
 * @property session Session storage
 */
object IPSession {
    private val session: MutableMap<String, Int> = mutableMapOf()

    /**
     * Add 1 to that ip address. If not found, start from 0 and add 1.
     *
     * @param ip IP address of client.
     */
    fun addCount(ip: String) {
        session[ip] = (session[ip] ?: 0) + 1
    }

    /**
     *  Check IP address exceed record limit, 5. If not found, handle as 0.
     *
     *  @param ip IP address of client.
     *  @return True if not exceed, else False
     */
    fun checkNotExceed(ip: String) = (session[ip] ?: 0) < 5
}