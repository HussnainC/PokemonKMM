package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeX(
    @SerialName("name")
    var name: String?,
    @SerialName("url")
    var url: String?
)