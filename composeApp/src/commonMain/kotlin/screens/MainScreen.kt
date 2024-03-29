package screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.rememberDominantColorState
import components.ColorBackground
import components.Loader
import components.PokemonWaterMark
import enums.Destinations
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import models.Pokemons
import models.Result
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import sealdClasses.DataLoader
import utils.UrlHelper
import viewModels.PikaViewModel

@Composable
fun MainScreen(
    navigator: Navigator,
    viewModel: PikaViewModel = koinViewModel(vmClass = PikaViewModel::class)
) {

    DisposableEffect(key1 = Unit) {
        viewModel.loadAllPokemon()
        onDispose {

        }
    }
    val pokemonsLoader = viewModel.pokemons.collectAsState()

    val isLoading by remember {
        derivedStateOf {
            pokemonsLoader.value is DataLoader.Loading
        }
    }
    val pokemons by remember {
        derivedStateOf {
            if (pokemonsLoader.value is DataLoader.Success) {
                (pokemonsLoader.value as DataLoader.Success<Pokemons>).result.results ?: listOf()
            } else {
                listOf()
            }
        }
    }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(color = ColorBackground)) {
            PokemonWaterMark(
                modifier = Modifier.align(Alignment.TopEnd).size(260.dp).offset(80.dp, (-20).dp)
                    .alpha(0.7f)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 40.dp).fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    text = "Pokedex",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )

                if (isLoading) {
                    Loader(70.dp)
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth().weight(1f),
                        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp)
                    ) {
                        items(pokemons, key = Result::id) {
                            PokiItem(it, onItemClick = { model, color ->
                                viewModel.selectPokemon(model, color)
                                navigator.navigate(Destinations.Detail.name)
                            })
                        }
                    }
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PokiItem(item: Result, onItemClick: (Result, Color) -> Unit) {
    val counter by remember {
        derivedStateOf {
            if (item.id < 10) {
                "#00${item.id}"
            } else if (item.id < 100) {
                "#0${item.id}"
            } else {
                "#${item.id}"
            }
        }
    }
    val url by remember {
        derivedStateOf {
            UrlHelper.getImageUrl(item.url)
        }
    }

    val paletteState =
        rememberDominantColorState(loader = NetworkLoader())

    LaunchedEffect(url) {
        paletteState.updateFrom(Url(url))
    }


    Card(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp).fillMaxWidth()
            .height(130.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        onClick = {
            onItemClick(item, paletteState.color)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(
                color = animateColorAsState(
                    paletteState.color,
                    animationSpec = tween(100, easing = LinearOutSlowInEasing)
                ).value
            )
        ) {
            PokemonWaterMark(
                modifier = Modifier.rotate(40f).padding(5.dp).align(Alignment.BottomEnd)
                    .size(70.dp).alpha(0.1f), color = Color.Black
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    counter,
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp).align(Alignment.End).alpha(0.3f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            (item.name ?: "No Name").replaceFirstChar { it.uppercaseChar() },
                            style = MaterialTheme.typography.h6.copy(
                                color = Color.White, fontSize = TextUnit(
                                    12f,
                                    TextUnitType.Sp
                                )
                            ),
                            modifier = Modifier.background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                                .padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(modifier = Modifier.fillMaxHeight()) {
                        KamelImage(
                            resource = asyncPainterResource(url),
                            contentDescription = item.name,
                            onLoading = { progress ->
                                CircularProgressIndicator(
                                    progress
                                )
                            },
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.size(70.dp)
                                .align(alignment = Alignment.BottomEnd),
                            contentScale = ContentScale.Crop
                        )
                    }
                }


            }
        }
    }
}







