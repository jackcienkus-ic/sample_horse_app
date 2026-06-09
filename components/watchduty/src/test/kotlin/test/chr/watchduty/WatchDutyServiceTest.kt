package test.chr.watchduty

import org.chr.watchduty.WatchDutyService
import java.net.ServerSocket
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WatchDutyServiceTest {
    private val port = ServerSocket(0).use { it.localPort }

    @Test
    fun testLatestWithLocalFixture() {
        val fake = FakeService(port)
        fake.start()

        val latest = WatchDutyService("http://localhost:$port").latest()
        assertEquals(393, latest.size)

        fake.stop()
    }

    @Test
    fun testLatestWithScrapedData() {
        val scrapedPort = ServerSocket(0).use { it.localPort }
        val scrapedBody = fetchFromWatchDuty()
        val fake = FakeService(scrapedPort, scrapedBody)
        fake.start()

        val latest = WatchDutyService("http://localhost:$scrapedPort").latest()
        assertTrue(latest.isNotEmpty(), "Expected scraped events to be non-empty")

        fake.stop()
    }

    private fun fetchFromWatchDuty(): String {
        val uri = URI.create("https://api.watchduty.org/api/v1/geo_events/?geo_event_types=wildfire,location")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .header("Accept", "application/json")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36")
            .header("Accept-Language", "en-US,en;q=0.9")
            .build()
        val response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString())
        check(response.statusCode() == 200) { "Watch Duty scrape failed: ${response.statusCode()}" }
        return response.body()
    }
}
