package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerialName("ability")
    var ability: AbilityX?,
    @SerialName("is_hidden")
    var isHidden: Boolean?,
    @SerialName("slot")
    var slot: Int?
)