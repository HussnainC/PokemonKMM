package screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.ColorBackground
import components.PokemonWaterMark
import enums.Destinations
import kotlinx.coroutines.delay
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

@Composable
fun SplashScreen(navigator: Navigator) {
    LaunchedEffect(key1 = true) {
        delay(3000L)
        navigator.navigate(
            Destinations.Home.name, options = NavOptions(
                popUpTo = PopUpTo(
                    route = Destinations.Splash.name,
                    inclusive = true,
                )
            )
        )
    }
    val rotationAnimation by rememberInfiniteTransition(label = "Rotation Animation").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Rotation Animation"
    )
    val scaleAnimation by rememberInfiniteTransition(label = "Scale Animation").animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Scale Animation"
    )
    MaterialTheme {
        Box(
            modifier = Modifier.background(
                color = ColorBackground
            ).fillMaxSize()
        ) {
            PokemonWaterMark(
                modifier = Modifier.align(Alignment.TopEnd).size(260.dp).offset(80.dp, (-20).dp)
                    .alpha(0.1f), color = Color.Gray
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(
                    "Pokemon",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
                )
                PokemonWaterMark(
                    modifier = Modifier.scale(scaleAnimation).size(70.dp).alpha(0.6f).rotate(rotationAnimation)
                       ,
                    color = Color.Gray
                )
            }
        }


    }
}

