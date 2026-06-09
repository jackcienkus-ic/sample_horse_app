package test.chr.watchduty

import io.initialcapacity.testserver.FakeServer

class FakeService(port: Int) : FakeServer(port) {
    override fun registerContexts() {
        context("/api/v1/geo_events") { exchange ->
            val success = testResource("/latest.json")
            exchange.sendResponse(200, success)
        }
    }
}
