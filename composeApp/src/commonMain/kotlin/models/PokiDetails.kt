package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokiDetails(
    @SerialName("abilities")
    var abilities: List<Ability>?,
    @SerialName("base_experience")
    var baseExperience: Int?,
    @SerialName("cries")
    var cries: Cries?,
    @SerialName("forms")
    var forms: List<Form>?,
    @SerialName("game_indices")
    var gameIndices: List<GameIndice>?,
    @SerialName("height")
    var height: Int?,
    @SerialName("id")
    var id: Int?,
    @SerialName("is_default")
    var isDefault: Boolean?,
    @SerialName("location_area_encounters")
    var locationAreaEncounters: String?,
    @SerialName("moves")
    var moves: List<Move>?,
    @SerialName("name")
    var name: String?,
    @SerialName("order")
    var order: Int?,
    @SerialName("species")
    var species: Species?,
    @SerialName("sprites")
    var sprites: Sprites?,
    @SerialName("stats")
    var stats: List<Stat>?,
    @SerialName("types")
    var types: List<Type>?,
    @SerialName("weight")
    var weight: Int?
)