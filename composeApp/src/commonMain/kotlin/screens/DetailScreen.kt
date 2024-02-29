package screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import components.PokemonWaterMark
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import utils.UrlHelper
import viewModels.PikaViewModel

@Composable
fun DetailScreen(
    navigator: Navigator,
    viewModel: PikaViewModel = koinViewModel(vmClass = PikaViewModel::class)
) {
    val selectedPokemon by viewModel.selectedPokemon.collectAsState()
    val pokemonColor by viewModel.selectedColor.collectAsState()
    val categories: List<String> by remember {
        derivedStateOf {
            listOf(
                "Category 1",
                "Category Two",
                "Category Three",
                "Category Four",
                "Category Five",
                "Category 6"
            )
        }
    }
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val url = remember(key1 = selectedPokemon) {
        derivedStateOf {
            if (selectedPokemon != null) {
                UrlHelper.getImageUrl(selectedPokemon?.url)
            } else {
                null
            }
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
                    modifier = Modifier.align(Alignment.TopEnd).size(260.dp).offset(60.dp, 20.dp)
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
                KamelImage(
                    resource = asyncPainterResource(url.value ?: Icons.Filled.Info),
                    contentDescription = selectedPokemon?.name ?: "",
                    onLoading = { progress ->
                        CircularProgressIndicator(
                            progress
                        )
                    }, contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().size(300.dp).padding(top = 15.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )
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
                        contentPadding = PaddingValues(horizontal = 10.dp)
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
                                tween(500, easing = EaseInBounce)
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
                                    modifier = Modifier.onGloballyPositioned {
                                        indicatorSize.value = it.size / 2
                                    },
                                    text = it,
                                    color = Color.Black, style = MaterialTheme.typography.body2
                                )
                            }

                        }
                    }
                }
            }


        }
    }
}