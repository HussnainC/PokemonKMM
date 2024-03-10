package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UltraSunUltraMoon(
    @SerialName("front_default")
    var frontDefault: String?,

    @SerialName("front_shiny")
    var frontShiny: String?
)