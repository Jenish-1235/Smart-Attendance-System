package network

import io.ktor.client.engine.cio.CIO
import io.ktor.client.*;
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import io.ktor.client.request.*

class JwtClient(
    private val AppId: String,
) {

    private val client = HttpClient(CIO)
    fun getJwt(): String = runBlocking {
        val response : HttpResponse = client.get("localhost:8080/jwt") {
            headers{
                append("X-APP-ID", AppId);
            }
        }
        response.bodyAsText()
    }
}