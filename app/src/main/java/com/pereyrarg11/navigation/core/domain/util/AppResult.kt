package com.pereyrarg11.navigation.core.domain.util

sealed interface AppResult<out D, out E : AppError> {
    data class Success<out D>(val data: D) : AppResult<D, Nothing>
    data class Error<out E : AppError>(val error: E) : AppResult<Nothing, E>
}

inline fun <T, E : AppError, R> AppResult<T, E>.map(mapper: (T) -> R): AppResult<R, E> {
    return when (this) {
        is AppResult.Error -> AppResult.Error(error)
        is AppResult.Success -> AppResult.Success(mapper(data))
    }
}

typealias EmptyAppResult<E> = AppResult<Unit, E>

fun <T, E : AppError> AppResult<T, E>.asEmptyAppResult(): EmptyAppResult<E> {
    return map { }
}
