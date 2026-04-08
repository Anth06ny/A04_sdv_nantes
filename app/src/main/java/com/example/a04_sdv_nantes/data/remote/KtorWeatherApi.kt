package com.example.a04_sdv_nantes.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//Suspend sera expliqué dans le chapitre des coroutines
suspend fun main() {
    val res = KtorWeatherApi.loadWeathers("Nice")

    for (r in res) {
        println(r.getResume())
    }

//    println("------")
//    println(KtorUserApi.loadUsers().joinToString("\n\n"))
//    KtorUserApi.close()
}

object KtorWeatherApi {
    private const val API_URL =
        "https://api.openweathermap.org/data/2.5/find?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

    //Création et réglage du client
    private val client = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    //GET Le JSON reçu sera parser en List<MuseumDTO>,
    //Crash si le JSON ne correspond pas
    suspend fun loadWeathers(cityName: String): List<WeatherEntity> {

        val response = client.get(API_URL + cityName) {
//            headers {
//                append("Authorization", "Bearer YOUR_TOKEN")
//                append("Custom-Header", "CustomValue")
//            }
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val list = response.body<WeatherAPIResult>().list
        list.forEach {
            it.weather.forEach {
                it.icon = "https://openweathermap.org/img/wn/${it.icon}@4x.png"
            }
        }
        return list
    }


    //Ferme le Client mais celui ci ne sera plus utilisable. Uniquement pour le main
    fun close() = client.close()
}

//DATA CLASS

//Possible qu'il y ait besoin de cette annotation en fonction du compilateur
@Serializable //KotlinX impose cette annotation
data class WeatherAPIResult(val list: List<WeatherEntity>)

@Serializable //KotlinX impose cette annotation
data class WeatherEntity(
    val name: String,
    val id: Int,
    val main: TempEntity,
    val wind: WindEntity,
    val weather: List<DescriptionEntity>
) {
    fun getResume() = """
            Il fait ${main.temp}° à $name (id=$id) avec un vent de ${wind.speed} m/s
            -Description : ${weather.firstOrNull()?.description ?: "-"}
            -Icône : ${weather.firstOrNull()?.icon ?: "-"}
        """.trimIndent()
}

@Serializable
data class TempEntity(var temp: Double)

@Serializable
data class WindEntity(var speed: Double)

@Serializable
data class DescriptionEntity(var description: String, var icon: String)

