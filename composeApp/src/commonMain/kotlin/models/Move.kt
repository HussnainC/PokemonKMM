package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Move(
    @SerialName("move")
    var move: MoveX?,
    @SerialName("version_group_details")
    var versionGroupDetails: List<VersionGroupDetail>?
)