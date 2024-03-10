package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kmpalette.loader.NetworkLoader
import com.kmpalette.rememberDominantColorState
import components.AboutText
import components.Loader
import components.Move
import components.PokemonWaterMark
import components.State
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import kotlinx.coroutines.delay
import models.PokiDetails
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import sealdClasses.DataLoader
import utils.UrlHelper
import viewModels.PikaViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navigator: Navigator,
    viewModel: PikaViewModel = koinViewModel(vmClass = PikaViewModel::class)
) {
    val selectedPokemon by viewModel.selectedPokemon.collectAsState()
    val pokemonColor by viewModel.selectedColor.collectAsState()

    val pokemonDetail = viewModel.pokeInfo.collectAsState()

    val url = remember(key1 = selectedPokemon) {
        derivedStateOf {
            if (selectedPokemon != null) {
                UrlHelper.getImageUrl(selectedPokemon?.url)
            } else {
                null
            }
        }
    }



    val categories: List<String> by remember {
        derivedStateOf {
            listOf(
                "About",
                "Base Stats",
                "Moves"
            )
        }
    }
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val pagerState = rememberPagerState { categories.size }

    LaunchedEffect(key1 = selectedCategory) {
        val index = categories.indexOf(selectedCategory)
        if (index != pagerState.currentPage) {
            pagerState.animateScrollToPage(
                index,
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        }
    }

    DisposableEffect(key1 = Unit) {
        viewModel.loadPokemonAbout()
        onDispose {

        }
    }

    val scaleTarget = remember {
        mutableStateOf(0f)
    }
    val scaleAnimation = animateFloatAsState(
        scaleTarget.value,
        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
    )
    val isAnimate = remember {
        mutableStateOf(false)
    }
    val rotationAnimation = animateFloatAsState(
        if (isAnimate.value)
            120f
        else
            0f,
        animationSpec = tween(
            1000,
            easing = LinearOutSlowInEasing
        )
    )
    val counter by remember {
        derivedStateOf {
            selectedPokemon?.let {
                if (it.id < 10) {
                    "#00${it.id}"
                } else if (it.id < 100) {
                    "#0${it.id}"
                } else {
                    "#${it.id}"
                }
            } ?: run {
                "#000"
            }

        }
    }
    LaunchedEffect(key1 = true) {
        delay(100)
        scaleTarget.value = 1f
        isAnimate.value = true
    }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier.fillMaxSize().blur(10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        color = animateColorAsState(
                            pokemonColor,
                            animationSpec = tween(500, easing = LinearOutSlowInEasing)
                        ).value
                    )
                )
                PokemonWaterMark(
                    modifier = Modifier.align(Alignment.TopEnd).size(260.dp).offset(60.dp, 50.dp)
                        .alpha(0.1f).scale(
                            scaleAnimation.value
                        ).rotate(
                            rotationAnimation.value
                        ), color = Color.Black
                )
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.padding(top = 40.dp).padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }, content = {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    })
                }
                Row(
                    modifier = Modifier.padding(top = 20.dp).padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        (selectedPokemon?.name
                            ?: "No Name").replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.h6.copy(
                            color = Color.White
                        ),
                        modifier = Modifier.background(
                            shape = RoundedCornerShape(10.dp),
                            brush = SolidColor(Color.LightGray),
                            alpha = 0.3f
                        ).padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        counter,
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        modifier = Modifier.padding(horizontal = 5.dp).alpha(0.5f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    KamelImage(
                        resource = asyncPainterResource(url.value ?: Icons.Filled.Info),
                        contentDescription = selectedPokemon?.name ?: "",
                        onLoading = { progress ->
                            CircularProgressIndicator(
                                progress
                            )
                        }, contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f).size(300.dp).padding(top = 15.dp),
                        contentScale = ContentScale.Fit
                    )

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        items(categories, key = { it.hashCode() }) {
                            val scaleValue = remember {
                                derivedStateOf {
                                    if (selectedCategory == it) {
                                        1f
                                    } else {
                                        0f
                                    }
                                }
                            }

                            val bounceScaleAnimation = animateFloatAsState(
                                scaleValue.value,
                                tween(500, easing = LinearOutSlowInEasing)
                            )
                            val indicatorSize = remember {
                                mutableStateOf(IntSize(0, 0))
                            }
                            Box(
                                modifier = Modifier.padding(horizontal = 10.dp).clip(
                                    RoundedCornerShape(15.dp)
                                ).clickable {
                                    viewModel.updateSelection(it)
                                },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier.scale(bounceScaleAnimation.value)
                                        .height(indicatorSize.value.height.dp)
                                        .width(indicatorSize.value.width.dp)
                                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
                                        .background(
                                            color = pokemonColor,
                                            shape = RoundedCornerShape(15.dp)
                                        )

                                )
                                Text(
                                    modifier = Modifier
                                        .onGloballyPositioned {
                                            indicatorSize.value = it.size.div(2)
                                        },
                                    text = it,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.button.copy(
                                        fontSize = TextUnit(
                                            17f,
                                            TextUnitType.Sp
                                        )
                                    )
                                )
                            }

                        }
                    }
                    HorizontalPager(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        state = pagerState,
                        pageSpacing = 0.dp,
                        userScrollEnabled = false,
                        reverseLayout = false,
                        contentPadding = PaddingValues(0.dp),
                        beyondBoundsPageCount = 0,
                        pageSize = PageSize.Fill,
                        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                        key = {
                            it
                        },
                        pageContent = {
                            when (it) {
                                0 -> {
                                    AboutPage(pokemonDetail)
                                }

                                1 -> {
                                    BaseStatePage(pokemonDetail)
                                }


                                2 -> {
                                    MovesPage(pokemonDetail)
                                }
                            }
                        }
                    )
                }
            }


        }
    }
}

data class About(
    val species: String,
    val height: String,
    val weight: String,
    val abilities: String,
    val types: String
)

@Composable
private fun AboutPage(detail: State<DataLoader<PokiDetails>>) {
    val pokemonDetail by remember {
        derivedStateOf {
            detail.value
        }
    }

    val isLoading by remember {
        derivedStateOf {
            pokemonDetail is DataLoader.Loading || pokemonDetail is DataLoader.Init
        }
    }
    val aboutInfo by remember {
        derivedStateOf {
            if (pokemonDetail is DataLoader.Success) {
                val result = pokemonDetail as DataLoader.Success<PokiDetails>
                About(
                    result.result.species?.name ?: "",
                    "${result.result.height}",
                    "${result.result.weight}",
                    abilities = result.result.abilities?.joinToString {
                        (it.ability?.name ?: "")
                    } ?: "", result.result.types?.joinToString {
                        (it.type?.name ?: "")
                    } ?: ""
                )
            } else {
                About(
                    "",
                    "",
                    "",
                    "", ""
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
            .padding(horizontal = 20.dp).padding(top = 30.dp)
    ) {
        AboutText("Species", aboutInfo.species, isLoading)
        AboutText("Height", aboutInfo.height, isLoading)
        AboutText("Weight", aboutInfo.weight, isLoading)
        AboutText("Abilities", aboutInfo.abilities, isLoading)
        AboutText("Types", aboutInfo.species, isLoading)

    }
}


@Composable
private fun BaseStatePage(detail: State<DataLoader<PokiDetails>>) {
    val pokemonDetail by remember {
        derivedStateOf {
            detail.value
        }
    }

    val isLoading by remember {
        derivedStateOf {
            pokemonDetail is DataLoader.Loading || pokemonDetail is DataLoader.Init
        }
    }
    val name by remember {
        derivedStateOf {
            if (pokemonDetail is DataLoader.Success) {
                val result = pokemonDetail as DataLoader.Success<PokiDetails>
                "${result.result.name}"
            } else {
                ""
            }
        }
    }
    val states by remember {
        derivedStateOf {
            if (pokemonDetail is DataLoader.Success) {
                val result = pokemonDetail as DataLoader.Success<PokiDetails>
                result.result.stats ?: listOf()
            } else {
                listOf()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp).padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Loader(40.dp)
        }
        AnimatedVisibility(modifier = Modifier.fillMaxWidth().weight(1f), visible = !isLoading) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(states, key = { it.hashCode() }) {
                        State(it)
                    }
                }
                Text(
                    "Type defenses",
                    color = Color.Black,
                    fontSize = TextUnit(
                        20f,
                        TextUnitType.Sp
                    ), modifier = Modifier.padding(horizontal = 15.dp).fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "The effectiveness of each type on ${name}.",
                    color = Color.LightGray,
                    fontSize = TextUnit(
                        18f,
                        TextUnitType.Sp
                    ),
                    modifier = Modifier.padding(horizontal = 15.dp)
                        .padding(top = 10.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

            }


        }

    }
}


@Composable
private fun MovesPage(detail: State<DataLoader<PokiDetails>>) {
    val pokemonDetail by remember {
        derivedStateOf {
            detail.value
        }
    }

    val isLoading by remember {
        derivedStateOf {
            pokemonDetail is DataLoader.Loading || pokemonDetail is DataLoader.Init
        }
    }

    val moves by remember {
        derivedStateOf {
            if (pokemonDetail is DataLoader.Success) {
                val result = pokemonDetail as DataLoader.Success<PokiDetails>
                result.result.moves ?: listOf()
            } else {
                listOf()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp).padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Loader(40.dp)
        }
        AnimatedVisibility(modifier = Modifier.fillMaxWidth().weight(1f), visible = !isLoading) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(moves, key = { it.hashCode() }) {
                    Move(it)
                }
            }

        }

    }
}



