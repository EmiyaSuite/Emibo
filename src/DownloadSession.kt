package rs.ij

data class DownloadSession (
        val downloadHistory: MutableMap<String, Int> = mutableMapOf()
)