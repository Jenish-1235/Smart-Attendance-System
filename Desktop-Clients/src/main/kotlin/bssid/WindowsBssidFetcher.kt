package bssid

import java.io.BufferedReader

class WindowsBssidFetcher : BssidFetcher {
    override fun getBssid(): String? {
        return try {
            println("kya tatti he ye ?")
            val process = ProcessBuilder("cmd.exe", "/c", "netsh wlan show interfaces").start()
            val output = process.inputStream.bufferedReader().use(BufferedReader::readLines)
            println(output)
            val bssidLine = output.firstOrNull { it.trim().contains("AP BSSID") }
            println(bssidLine)
            val rawBssid = bssidLine?.substringAfter(":")?.trim()
            val normalized = rawBssid
                ?.split(":")
                ?.joinToString("-") { it.uppercase() }
            normalized
        } catch (e: Exception) {
            println("❌ Windows BSSID fetch failed: ${e.message}")
            null
        }
    }
}