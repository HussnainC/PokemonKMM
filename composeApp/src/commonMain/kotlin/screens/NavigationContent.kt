package screens

import androidx.compose.runtime.Composable
import enums.Destinations
import koinModule.factoryModule
import koinModule.singleModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinApplication

@Composable
fun NavigationContent() = PreComposeApp {
    KoinApplication(moduleList = {
        listOf(singleModule, factoryModule)
    }, content = {
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = Destinations.Splash.name
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
        }
    })

}