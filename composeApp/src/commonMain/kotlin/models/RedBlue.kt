package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedBlue(
    @SerialName("back_default")
    var backDefault: String?,
    @SerialName("back_gray")
    var backGray: String?,
    @SerialName("back_transparent")
    var backTransparent: String?,
    @SerialName("front_default")
    var frontDefault: String?,
    @SerialName("front_gray")
    var frontGray: String?,
    @SerialName("front_transparent")
    var frontTransparent: String?
)