package org.chr.watchduty

fun main() {
    val test = WatchDutyService().latestHTTP()
    println(test)
    val tester = test.filter {
        EventClassification().isInColorado(it.lat!!, it.lng!!, 50.0)
    }
    val fireList: MutableList<WatchDutyEvent> = mutableListOf()
    for (fire in tester) {
        fire.county=EventClassification().assignCounty(fire)
    }
    println(tester)
    println(tester.size)

}
