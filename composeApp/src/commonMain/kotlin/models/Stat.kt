package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    @SerialName("base_stat")
    var baseStat: Int?,
    @SerialName("effort")
    var effort: Int?,
    @SerialName("stat")
    var stat: StatX?
)