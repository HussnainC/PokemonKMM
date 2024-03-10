package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameIndice(
    @SerialName("game_index")
    var gameIndex: Int?,
    @SerialName("version")
    var version: Version?
)