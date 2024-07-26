package ir.androidcoder.hammasir.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import ir.androidcoder.hammasir.screen.ui.theme.HamMasirTheme
import ir.androidcoder.hammasir.viewModel.MapViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HamMasirTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MapScreen(mapViewModel)
                }
            }
        }
    }
}


@Composable
fun MapScreen(mapViewModel: MapViewModel) {
    AndroidView(factory = { context ->
        MapView(context).also { mapView ->
            mapViewModel.initializeMap(mapView)
            mapViewModel.centerMapAt(GeoPoint(35.694669, 52.031546) , 20.0)
        }
    }, modifier = Modifier.fillMaxSize())


    // Cleanup when this composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mapViewModel.mMap.onDetach()
        }
    }
}