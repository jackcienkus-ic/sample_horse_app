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
    fun testLatestHTTP() {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.watchduty.org/api/v1/geo_events/?geo_event_types=wildfire,location"))
            .GET()
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36")
            .header("Accept-Language", "en-US,en;q=0.9")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        assertEquals(200, response.statusCode())
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