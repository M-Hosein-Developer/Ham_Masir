package ir.androidcoder.hammasir.screen.feature

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import ir.androidcoder.hammasir.viewModel.MapViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay

@Composable
fun MapScreen(mapViewModel: MapViewModel){


    Column(
        Modifier.fillMaxSize()
    ) {

        MapSetting(mapViewModel)

    }

}

@Composable
fun MapSetting(mapViewModel: MapViewModel) {
    val context = LocalContext.current
    var hasLocationPermission by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                hasLocationPermission = true
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    if (hasLocationPermission) {
        AndroidView(factory = { context ->
            MapView(context).also { mapView ->
                mapViewModel.initializeMap(mapView)
                coroutineScope.launch {
                    val userLocation = mapViewModel.getUserLocation(context)
                    userLocation?.let {
                        mapViewModel.centerMapAt(GeoPoint(it.first, it.second), 18.0)
                    }

                    mapView.overlays.add(object : Overlay() {
                        override fun draw(c: Canvas?, osmv: MapView?, shadow: Boolean) {

                        }

                        override fun onSingleTapConfirmed(
                            e: MotionEvent?,
                            mapView: MapView?
                        ): Boolean {
                            e?.let {
                                val point = mapView?.projection?.fromPixels(
                                    e.x.toInt(),
                                    e.y.toInt()
                                ) as GeoPoint
                                mapViewModel.addMarkerClicked(point)
                            }
                            return true
                        }
                    })
                }
            }
        }, modifier = Modifier.fillMaxSize())
    } else {

        Box(modifier = Modifier.fillMaxSize()) {

            AndroidView(factory = { context ->
                MapView(context).also { mapView ->
                    mapViewModel.initializeMap(mapView)
                    coroutineScope.launch {

                        mapViewModel.centerMapAt(GeoPoint(0.0, 0.0), 18.0)

                        mapView.overlays.add(object : Overlay() {
                            override fun draw(c: Canvas?, osmv: MapView?, shadow: Boolean) {

                            }

                            override fun onSingleTapConfirmed(
                                e: MotionEvent?,
                                mapView: MapView?
                            ): Boolean {
                                e?.let {
                                    val point = mapView?.projection?.fromPixels(
                                        e.x.toInt(),
                                        e.y.toInt()
                                    ) as GeoPoint
                                    mapViewModel.addMarkerClicked(point)
                                }
                                return true
                            }
                        })
                    }
                }
            }, modifier = Modifier.fillMaxSize())

            Text(
                text = "اجازه استفاده از موقعیت مکانی خود را برای این اپلیکیشن فعال کنید",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

        }

    }

    DisposableEffect(Unit) {
        onDispose {
            mapViewModel.mMap.onDetach()
        }
    }
}