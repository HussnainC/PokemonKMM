package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupDetail(
    @SerialName("level_learned_at")
    var levelLearnedAt: Int?,
    @SerialName("move_learn_method")
    var moveLearnMethod: MoveLearnMethod?,
    @SerialName("version_group")
    var versionGroup: VersionGroup?
)