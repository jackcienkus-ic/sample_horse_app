package org.chr.watchduty

fun main() {
    val test = WatchDutyService("https://api.watchduty.org").latestHTTP()
    println(test)
    val tester = test.filter {
        EventClassification().isInColorado(it.lat!!, it.lng!!, 50.0)
    }
    for (fire in tester) {
        fire.county=EventClassification().assignCounty(fire)
    }
    println(tester)
    println(tester.size)

}
