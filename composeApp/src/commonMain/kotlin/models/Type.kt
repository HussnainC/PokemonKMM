package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type(
    @SerialName("slot")
    var slot: Int?,
    @SerialName("type")
    var type: TypeX?
)