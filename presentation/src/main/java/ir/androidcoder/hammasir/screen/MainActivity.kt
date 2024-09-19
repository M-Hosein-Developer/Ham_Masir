package ir.androidcoder.hammasir.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ir.androidcoder.hammasir.R
import ir.androidcoder.hammasir.screen.feature.MapScreen
import ir.androidcoder.hammasir.screen.feature.SearchScreen
import ir.androidcoder.hammasir.screen.feature.SettingScreen
import ir.androidcoder.hammasir.screen.ui.theme.HamMasirTheme
import ir.androidcoder.hammasir.util.MyScreen
import ir.androidcoder.hammasir.viewModel.MapViewModel
import ir.androidcoder.hammasir.viewModel.SearchViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mapViewModel : MapViewModel by viewModels()
    private val searchViewModel : SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HamMasirTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyScreen(mapViewModel , searchViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScreen(mapViewModel: MapViewModel , searchViewModel: SearchViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(MyScreen.SettingScreen.route) },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "تنظیمات")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "پیدا کردن")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(MyScreen.MapScreen.route) },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.finding_map),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "نقشه")
                    }
                )

            }

        }
    ) {

        NavHost(
            navController = navController,
            startDestination = MyScreen.MapScreen.route + "/{latLocation}/{longLocation}/{name}"
        ) {

            composable(
                route = MyScreen.MapScreen.route + "/{latLocation}/{longLocation}/{name}",
                arguments = listOf(navArgument("latLocation") { type = NavType.StringType },
                    navArgument("longLocation") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType }),
                enterTransition = { slideInHorizontally(initialOffsetX = { 500 }) + fadeIn() },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -500 }) + fadeOut() },
            ) {
                MapScreen(
                    mapViewModel,
                    navController,
                    searchViewModel,
                    it.arguments?.getString("latLocation"),
                    it.arguments?.getString("longLocation"),
                    it.arguments?.getString("name")
                )
            }

            composable(
                route = MyScreen.SearchScreen.route,
            ) {
                SearchScreen(navController, searchViewModel)
            }

            composable(
                route = MyScreen.SettingScreen.route,
            ){
                SettingScreen()
            }

        }

    }

}


