package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
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
    val url = remember(key1 = selectedPokemon) {
        derivedStateOf {
            if (selectedPokemon != null) {
                UrlHelper.getImageUrl(selectedPokemon?.url)
            } else {
                null
            }
        }
    }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.padding(top = 20.dp).padding(horizontal = 12.dp)
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
            }
//            Text(selectedPokemon?.name ?: "No Name")
//            if (url.value != null)
//                KamelImage(
//                    resource = asyncPainterResource(url.value!!),
//                    contentDescription = selectedPokemon?.name ?: "No Name",
//                    onLoading = { progress ->
//                        CircularProgressIndicator(
//                            progress
//                        )
//                    },
//                    modifier = Modifier.size(200.dp),
//                    contentScale = ContentScale.Crop
//                )
        }
    }
}