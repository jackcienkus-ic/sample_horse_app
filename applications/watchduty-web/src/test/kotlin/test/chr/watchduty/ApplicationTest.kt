package test.chr.watchduty

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.chr.watchduty.WatchDutyService
import org.chr.watchduty.configureFreemarker
import org.chr.watchduty.configureRouting
import java.net.ServerSocket
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `test root endpoint`() = testApplication {
        application {
            configureFreemarker()
            configureRouting(WatchDutyService("http://unused"))
        }
        assertEquals(HttpStatusCode.OK, client.get("/").status)
    }
    @Ignore
    @Test
    fun `test html freemarker endpoint`() = testApplication {
        application {
            configureFreemarker()
            configureRouting(WatchDutyService("http://unused"))
        }
        assertEquals(HttpStatusCode.OK, client.get("/html-freemarker").status)
    }

    @Test
    fun `test fires endpoint`() {
        val port = ServerSocket(0).use { it.localPort }
        val fake = FakeService(port)
        fake.start()

        try {
            testApplication {
                application {
                    configureFreemarker()
                    configureRouting(WatchDutyService("http://localhost:$port"))
                }
                assertEquals(HttpStatusCode.OK, client.get("/fires").status)
                assertEquals(HttpStatusCode.OK, client.get("/fires?region=100").status)
            }
        } finally {
            fake.stop()
        }
    }
}
