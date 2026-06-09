package test.chr.watchduty

import org.chr.watchduty.Application
import java.net.ServerSocket
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    private val port = ServerSocket(0).use { it.localPort }
    private val fake = FakeService(port)

    @Test
    fun testApplication() {
        fake.start()

        val latest = Application().extracted("http://localhost:$port")
        assertEquals(393, latest.size)

        fake.stop()
    }
}
