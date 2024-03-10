package screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import enums.Destinations
import koinModule.factoryModule
import koinModule.singleModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.KoinApplication

@Composable
fun NavigationContent() = PreComposeApp {

    KoinApplication(moduleList = {
        listOf(singleModule, factoryModule)
    }, content = {
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = Destinations.Splash.name,
            navTransition = NavTransition(
                createTransition = fadeIn(
                    animationSpec = tween(
                        500,
                        easing = LinearEasing
                    )
                ), destroyTransition = fadeOut(
                    animationSpec = tween(
                        500,
                        easing = LinearEasing
                    )
                )
            )
        ) {
            scene(
                route = Destinations.Splash.name
            ) {
                SplashScreen(navigator)
            }
            scene(
                route = Destinations.Home.name
            ) {
                MainScreen(navigator)
            }

            scene(route = Destinations.Detail.name) {
                DetailScreen(navigator)
            }
        }
    })

}