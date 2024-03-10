package viewModels

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import models.Pokemons
import models.PokiDetails
import models.Result
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import repository.ApiRepository
import sealdClasses.DataLoader
import utils.DataHolder

class PikaViewModel(private val apiRepository: ApiRepository, val dataHolder: DataHolder) :
    ViewModel() {
    private val _selectedPokemon: MutableStateFlow<Result?> =
        MutableStateFlow(dataHolder.selectedPokemon)
    val selectedPokemon = _selectedPokemon.asStateFlow()

    private val _selectedColor: MutableStateFlow<Color> = MutableStateFlow(dataHolder.pokemonColor)
    val selectedColor = _selectedColor.asStateFlow()


    private val _selectedCategory: MutableStateFlow<String> = MutableStateFlow("About")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _pokemons = MutableStateFlow<DataLoader<Pokemons>>(DataLoader.Init)
    val pokemons = _pokemons.asStateFlow()

    private val _pokeInfo = MutableStateFlow<DataLoader<PokiDetails>>(DataLoader.Init)
    val pokeInfo = _pokeInfo.asStateFlow()


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

    fun selectPokemon(model: Result, color: Color) {
        dataHolder.pokemonColor = color
        dataHolder.selectedPokemon = model
    }

    fun updateSelection(category: String) {
        _selectedCategory.value = category
    }

    fun loadPokemonAbout() = viewModelScope.launch {
        dataHolder.selectedPokemon?.name?.let {
            if (pokemons.value is DataLoader.Init)
                apiRepository.loadPokemonDetail(it).dataInitializer(_pokeInfo)
        }
    }

    fun moveBackward() {

    }

    fun moveForward() {

    }
}