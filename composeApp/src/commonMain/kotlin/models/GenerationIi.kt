package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIi(
    @SerialName("crystal")
    var crystal: Crystal?,
    @SerialName("gold")
    var gold: Gold?,
    @SerialName("silver")
    var silver: Silver?
)