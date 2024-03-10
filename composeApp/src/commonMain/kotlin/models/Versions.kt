package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    @SerialName("generation-i")
    var generationI: GenerationI?,
    @SerialName("generation-ii")
    var generationIi: GenerationIi?,
    @SerialName("generation-iii")
    var generationIii: GenerationIii?,
    @SerialName("generation-iv")
    var generationIv: GenerationIv?,
    @SerialName("generation-v")
    var generationV: GenerationV?,
    @SerialName("generation-vi")
    var generationVi: GenerationVi?,
    @SerialName("generation-vii")
    var generationVii: GenerationVii?,
    @SerialName("generation-viii")
    var generationViii: GenerationViii?
)