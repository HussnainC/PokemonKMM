package repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import models.Pokemons
import models.PokiDetails
import sealdClasses.DataLoader
import webApi.ApiClient


class ApiRepository(private val apiClient: ApiClient) {
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    fun loadAllPokemons() = flow<DataLoader<Pokemons>> {
        val result = apiClient.client.get("$BASE_URL/pokemon?limit=300&offset=0").body<Pokemons>()
        result.results?.takeIf { it.isNotEmpty() }?.apply {
            forEachIndexed { index, result ->
                result.id = index + 1
            }
        }
        emit(DataLoader.Success(result))
    }.flowOn(Dispatchers.IO)

    fun loadPokemonDetail(pokemon: String) = flow<DataLoader<PokiDetails>> {
        val result = apiClient.client.get("$BASE_URL/pokemon/${pokemon}").body<PokiDetails>()
        emit(DataLoader.Success(result))
    }.flowOn(Dispatchers.IO)


}