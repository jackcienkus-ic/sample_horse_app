package test.chr.watchduty

import org.chr.watchduty.WatchDutyService
import kotlin.test.Test
import kotlin.test.assertEquals

class WatchDutyServiceTest() {

    @Test
    fun testLatest() {
        val latest = WatchDutyService().latest()
        assertEquals(2, latest.size)
    }
}