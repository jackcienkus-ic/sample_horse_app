package test.chr.watchduty

import org.chr.watchduty.Application
import kotlin.test.Test
import kotlin.test.assertNotNull

class ApplicationTest {

    @Test
    fun testApplication() {
        val extracted = Application().extracted()
        assertNotNull(extracted)
    }
}
