package ir.androidcoder.hammasir.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.androidcoder.hammasir.screen.feature.MapScreen
import ir.androidcoder.hammasir.screen.feature.SearchScreen
import ir.androidcoder.hammasir.screen.ui.theme.HamMasirTheme
import ir.androidcoder.hammasir.util.MyScreen
import ir.androidcoder.hammasir.viewModel.MapViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HamMasirTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyScreen(mapViewModel)
                }
            }
        }
    }
}

@Composable
fun MyScreen(mapViewModel: MapViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MyScreen.MapScreen.route) {

        composable(
            route = MyScreen.MapScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 500 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -500 }) + fadeOut() },
            ){
            MapScreen(mapViewModel , navController)
        }

        composable(
            route = MyScreen.SearchScreen.route,
            ){
            SearchScreen(navController)
        }

    }


}


