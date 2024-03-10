package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RubySapphire(
    @SerialName("back_default")
    var backDefault: String?,
    @SerialName("back_shiny")
    var backShiny: String?,
    @SerialName("front_default")
    var frontDefault: String?,
    @SerialName("front_shiny")
    var frontShiny: String?
)