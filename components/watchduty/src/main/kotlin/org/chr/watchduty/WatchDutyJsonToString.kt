package org.chr.watchduty

class WatchDutyJsonToString {
    fun readJSONFromResources(fileName: String): String? {
        return try{
            val inputStream = this::class.java.classLoader
                ?.getResourceAsStream(fileName)
            inputStream?.bufferedReader()?.readText()
        } catch (e: Exception) {
            null
        }
    }
}