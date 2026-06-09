package io.initialcapacity.testserver

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.URLDecoder
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

abstract class FakeServer(port: Int) {
    protected val server: HttpServer = HttpServer.create(InetSocketAddress(port), 0)

    val exchanges = mutableListOf<HttpExchange>()
    val errors: Deque<FakeError> = ConcurrentLinkedDeque()

    abstract fun registerContexts()

    fun start() {
        registerContexts()
        server.start()
    }

    fun stop() {
        server.stop(0)
    }

    protected fun context(path: String, handler: HttpHandler) {
        server.createContext(path) { exchange ->
            val body = exchange.requestBody.bufferedReader().readText()
            exchanges.add(exchange)
            exchange.setAttribute("body", body)
            val queued = errors.poll()
            if (queued != null) {
                exchange.sendResponse(queued.statusCode, queued.message)
            } else {
                handler.handle(exchange)
            }
        }
    }

    protected fun parseQuery(query: String): Map<String, String> {
        val pairs = query.split("&").filter { it.contains("=") }

        return pairs.associate { pair ->
            val (key, value) = pair.split("=", limit = 2)
            URLDecoder.decode(key, "UTF-8") to URLDecoder.decode(value, "UTF-8")
        }
    }

    protected fun HttpExchange.body(): String = getAttribute("body") as String

    protected fun HttpExchange.sendResponse(status: Int, body: String) {
        val bytes = body.toByteArray()
        sendResponseHeaders(status, bytes.size.toLong())
        responseBody.write(bytes)
        close()
    }

    protected fun testResource(resource: String): String =
        object {}.javaClass.getResourceAsStream(resource)!!.readAllBytes().let(::String)
}
