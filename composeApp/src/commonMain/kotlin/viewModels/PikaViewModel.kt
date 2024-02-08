package viewModels

import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import models.Pokemons
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import repository.ApiRepository
import sealdClasses.DataLoader

class PikaViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val _pokemons = MutableStateFlow<DataLoader<Pokemons>>(DataLoader.Loading)
    val pokemons = _pokemons.asStateFlow()

    fun loadAllPokemon() = viewModelScope.launch {
        apiRepository.loadAllPokemons().dataInitializer(_pokemons)
    }

    private suspend fun <T> Flow<DataLoader<T>>.dataInitializer(reference: MutableStateFlow<DataLoader<T>>) {
        onStart {
            reference.value = DataLoader.Loading
        }.catch {
            KtorSimpleLogger("KtorClient").debug("Error: ${it.message.toString()}")
            reference.value = DataLoader.Fail(Exception(it.message))
        }.collectLatest {
            KtorSimpleLogger("KtorClient").debug("Success")
            reference.value = it
        }
    }
}