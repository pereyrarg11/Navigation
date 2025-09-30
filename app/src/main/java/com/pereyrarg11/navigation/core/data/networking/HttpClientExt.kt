package com.pereyrarg11.navigation.core.data.networking

import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified Response : Any> HttpClient.get(
    baseUrl: String,
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): AppResult<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(baseUrl, route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    baseUrl: String,
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): AppResult<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(baseUrl, route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    baseUrl: String,
    route: String,
    body: Request,
): AppResult<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(baseUrl, route))
            setBody(body)
        }
    }
}

suspend inline fun <reified T> safeCall(performRequest: () -> HttpResponse): AppResult<T, DataError.Network> {
    // TODO: log error message to Crashlytics
    val response = try {
        performRequest()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return AppResult.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return AppResult.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return AppResult.Error(DataError.Network.UNKNOWN)
    }

    return responseToAppResult(response)
}

suspend inline fun <reified T> responseToAppResult(response: HttpResponse): AppResult<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> AppResult.Success(response.body<T>())
        // response DTO contains both message and error code, which must be handled by repository impl.
        400 -> AppResult.Success(response.body<T>())
        401 -> AppResult.Error(DataError.Network.UNAUTHORIZED)
        in 500..599 -> AppResult.Error(DataError.Network.SERVER_ERROR)
        else -> AppResult.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(
    baseUrl: String,
    route: String,
): String {
    return when {
        route.contains(baseUrl) -> route
        route.startsWith("/") -> baseUrl + route
        else -> "$baseUrl/$route"
    }
}
