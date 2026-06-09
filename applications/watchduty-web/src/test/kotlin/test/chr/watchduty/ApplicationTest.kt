package test.chr.watchduty

import org.chr.watchduty.Application
import java.net.ServerSocket
import kotlin.test.Test
import kotlin.test.assertNotNull


class ApplicationTest {

    private val port = ServerSocket(0).use { it.localPort }
    private val fake = FakeService(port)

    @Test
    fun testApplication() {
        fake.start()

        val baseurl = "http://localhost:$port"
        val extracted = Application().extracted(baseurl)
        assertNotNull(extracted)

        fake.stop()
    }
}
