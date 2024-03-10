package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVi(
    @SerialName("omegaruby-alphasapphire")
    var omegarubyAlphasapphire: OmegarubyAlphasapphire?,
    @SerialName("x-y")
    var xY: XY?
)