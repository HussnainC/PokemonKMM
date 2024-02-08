package models

import kotlinx.serialization.Serializable


@Serializable
data class Result(
    var name: String?,
    var url: String?
) {
    var id: Int = 0
}