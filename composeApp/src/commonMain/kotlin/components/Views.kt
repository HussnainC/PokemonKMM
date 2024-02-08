package components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

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