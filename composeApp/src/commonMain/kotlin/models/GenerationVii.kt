package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVii(
    @SerialName("icons")
    var icons: Icons?,
    @SerialName("ultra-sun-ultra-moon")
    var ultraSunUltraMoon: UltraSunUltraMoon?
)