package sealdClasses

sealed class DataLoader<out T> {
    data object Init : DataLoader<Nothing>()
    data object Loading : DataLoader<Nothing>()
    class Fail(val cause: Exception) : DataLoader<Nothing>()
    class Success<T : Any>(val result: T) : DataLoader<T>()
}