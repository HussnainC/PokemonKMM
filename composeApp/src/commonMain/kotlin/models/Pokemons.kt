package models

import kotlinx.serialization.Serializable


@Serializable
data class Pokemons(
    var count: Int?,
    var results: List<Result>?
)