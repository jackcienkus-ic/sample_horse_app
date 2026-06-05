package org.chr.watchduty

class WatchDutyService() {
    fun latest(): List<WatchDutyEvent> {

        // todo -
        // load from resources dir
        // load from http client
        return listOf(
            WatchDutyEvent(
                id = "1",
                type = "earthquake",
                name = "Earthquake in California"
            ),
            WatchDutyEvent(
                id = "2",
                type = "flood",
                name = "Flood in Texas"
            )
        )
    }
}
