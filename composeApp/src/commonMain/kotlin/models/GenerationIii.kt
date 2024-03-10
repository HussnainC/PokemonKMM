package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIii(
    @SerialName("emerald")
    var emerald: Emerald?,
    @SerialName("firered-leafgreen")
    var fireredLeafgreen: FireredLeafgreen?,
    @SerialName("ruby-sapphire")
    var rubySapphire: RubySapphire?
)