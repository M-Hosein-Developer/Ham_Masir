package ir.androidcoder.hammasir.util

sealed class MyScreen(val route: String) {

    data object MapScreen : MyScreen("map_screen")
    data object SearchScreen : MyScreen("search_screen")
    data object SettingScreen : MyScreen("setting_screen")

}