package fxc.dev.core.utils

sealed class Result<out T> {
    object Init : Result<Nothing>()
    object Start : Result<Nothing>()
    object Failure : Result<Nothing>()
    data class Success<out T>(val data: T?) : Result<T>()
}