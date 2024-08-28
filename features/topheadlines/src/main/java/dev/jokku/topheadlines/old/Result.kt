package dev.jokku.topheadlines.old

import dev.jokku.ui.UiErrorMessage

sealed interface Result<out T> {
    data class Success<out T>(
        val actualData: T
    ) : Result<T>

    data class Failure<out T>(
        val message: UiErrorMessage,
        val cachedData: T?
    ) : Result<T>
}