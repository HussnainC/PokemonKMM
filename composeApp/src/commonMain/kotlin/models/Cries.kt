package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cries(
    @SerialName("latest")
    var latest: String?,
    @SerialName("legacy")
    var legacy: String?
)