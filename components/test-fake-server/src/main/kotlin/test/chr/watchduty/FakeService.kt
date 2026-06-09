package test.chr.watchduty

import io.initialcapacity.testserver.FakeServer

class FakeService(port: Int, private val responseBody: String? = null) : FakeServer(port) {
    override fun registerContexts() {
        context("/api/v1/geo_events") { exchange ->
            val body = responseBody ?: testResource("/latest.json")
            exchange.sendResponse(200, body)
        }
    }
}
