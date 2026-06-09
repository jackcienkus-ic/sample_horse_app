package test.chr.watchduty

import org.chr.watchduty.WatchDutyService
import java.net.ServerSocket
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WatchDutyServiceTest() {
    private val port = ServerSocket(0).use { it.localPort }
    private val fake = FakeService(port)

    @Test
    fun testLatestResource() {
        val latest = WatchDutyService("https://api.watchduty.org").latestResources()
        assertEquals(393, latest.size)
    }



    @Test
    fun testLatest() {
        fake.start()

        val baseUrl = "http://localhost:$port"
        val watchDutyService = WatchDutyService(baseUrl)
        val latest = watchDutyService.latestHTTP()
        assertEquals(393, latest.size)

        fake.stop()
    }
}