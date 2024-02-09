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
import models.Result
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import repository.ApiRepository
import sealdClasses.DataLoader
import utils.DataHolder

class PikaViewModel(private val apiRepository: ApiRepository, private val dataHolder: DataHolder) :
    ViewModel() {
    val selectedPokemon: MutableStateFlow<Result?> = MutableStateFlow(dataHolder.selectedPokemon)

    private val _pokemons = MutableStateFlow<DataLoader<Pokemons>>(DataLoader.Init)
    val pokemons = _pokemons.asStateFlow()

    fun loadAllPokemon() = viewModelScope.launch {
        if (pokemons.value is DataLoader.Init)
            apiRepository.loadAllPokemons().dataInitializer(_pokemons)
    }

    private suspend fun <T> Flow<DataLoader<T>>.dataInitializer(reference: MutableStateFlow<DataLoader<T>>) {
        onStart {
            reference.value = DataLoader.Loading
        }.catch {
            reference.value = DataLoader.Fail(Exception(it.message))
        }.collectLatest {
            reference.value = it
        }
    }

    fun selectPokemon(model: Result) {
        dataHolder.selectedPokemon = model
    }
}