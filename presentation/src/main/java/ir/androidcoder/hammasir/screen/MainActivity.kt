package ir.androidcoder.hammasir.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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

        composable(MyScreen.MapScreen.route){
            MapScreen(mapViewModel , navController)
        }

        composable(MyScreen.SearchScreen.route){
            SearchScreen()
        }

    }


}


