package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIv(
    @SerialName("diamond-pearl")
    var diamondPearl: DiamondPearl?,
    @SerialName("heartgold-soulsilver")
    var heartgoldSoulsilver: HeartgoldSoulsilver?,
    @SerialName("platinum")
    var platinum: Platinum?
)