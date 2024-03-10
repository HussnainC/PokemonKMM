package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import models.Move
import models.Stat

@Composable
fun PokemonWaterMark(
    modifier: Modifier = Modifier,
    color: Color = ColorGray,
    innerColor: Color = Color.White
) {

    val width = remember {
        mutableStateOf(0f)
    }
    Box(
        modifier = modifier.graphicsLayer {
            width.value = this.size.width
        }.clip(CircleShape)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = color)
            drawLine(
                color = innerColor,
                start = Offset(0f, this.center.y),
                end = Offset(width.value, this.center.y),
                strokeWidth = width.value / 8
            )
            drawCircle(color = innerColor, radius = width.value / 4)
            drawCircle(color = color, radius = width.value / 6)
        }
    }
}

@Composable
fun AboutText(title: String, value: String, isLoading: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 15.dp)) {
        Text(
            title, modifier = Modifier.weight(1f), color = Color.Gray, fontSize = TextUnit(
                18f,
                TextUnitType.Sp
            ), textAlign = TextAlign.Start
        )
        Text(
            if (isLoading) "" else value,
            modifier = Modifier.weight(1.8f)
                .background(
                    color = if (isLoading) Color.LightGray.copy(alpha = 0.5f) else Color.Transparent,
                    RoundedCornerShape(10.dp)
                )
                .shimmerLoadingAnimation(isLoadingComplete = !isLoading),
            color = Color.Black,
            fontSize = TextUnit(
                18f,
                TextUnitType.Sp
            ),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Loader(size: Dp) {
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        PokemonWaterMark(
            modifier = Modifier.scale(scaleAnimation).size(size).alpha(0.6f)
                .rotate(rotationAnimation),
            color = Color.Gray
        )
    }
}

@Composable
fun State(state: Stat) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            state.stat?.name?.replaceFirstChar {
                it.uppercase()
            }?.replace(Regex("_-"), " ") ?: "",
            modifier = Modifier.padding(end = 5.dp).weight(1f),
            color = Color.Gray,
            fontSize = TextUnit(
                18f,
                TextUnitType.Sp
            ),
            textAlign = TextAlign.Start
        )
        Text(
            "${state.baseStat}",
            color = Color.Black,
            fontSize = TextUnit(
                18f,
                TextUnitType.Sp
            ),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        LinearProgressIndicator(
            modifier = Modifier.padding(start = 15.dp).weight(1.5f),
            color = if ((state.baseStat ?: 0) >= 50) Color.Green else Color.Red,
            strokeCap = StrokeCap.Round,
            progress = (state.baseStat ?: 0) / 100f,
            backgroundColor = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun Move(move: Move) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
    ) {
        Text(
            move.move?.name?.replaceFirstChar {
                it.uppercase()
            }?.replace(Regex("_-"), " ") ?: "",
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            fontSize = TextUnit(
                18f,
                TextUnitType.Sp
            ),
            textAlign = TextAlign.Start
        )
        Spacer(
            modifier = Modifier.padding(top = 5.dp).height(5.dp).background(color = Color.LightGray)
        )
    }
}

fun Modifier.shimmerLoadingAnimation(
    isLoadingComplete: Boolean,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    if (isLoadingComplete) {
        return this
    }
    return composed {
        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )
        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )
        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            )
        )
    }
}