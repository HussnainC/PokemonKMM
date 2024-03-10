package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crystal(
    @SerialName("back_default")
    var backDefault: String?,
    @SerialName("back_shiny")
    var backShiny: String?,
    @SerialName("back_shiny_transparent")
    var backShinyTransparent: String?,
    @SerialName("back_transparent")
    var backTransparent: String?,
    @SerialName("front_default")
    var frontDefault: String?,
    @SerialName("front_shiny")
    var frontShiny: String?,
    @SerialName("front_shiny_transparent")
    var frontShinyTransparent: String?,
    @SerialName("front_transparent")
    var frontTransparent: String?
)