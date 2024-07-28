package ir.androidcoder.hammasir.screen.feature

import android.graphics.Canvas
import android.view.MotionEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ir.androidcoder.hammasir.viewModel.MapViewModel
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
    AndroidView(factory = { context ->
        MapView(context).also { mapView ->
            mapViewModel.initializeMap(mapView)
            mapViewModel.centerMapAt(GeoPoint(35.694669, 52.031546) , 20.0)

            mapView.overlays.add(object : Overlay() {
                override fun draw(c: Canvas?, osmv: MapView?, shadow: Boolean) {
                    // نیازی به پیاده‌سازی این قسمت نیست
                }

                override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
                    e?.let {
                        val point = mapView?.projection?.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                        mapViewModel.addMarkerClicked(point)
                    }
                    return true
                }
            })

        }
    }, modifier = Modifier.fillMaxSize())


    // Cleanup when this composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mapViewModel.mMap.onDetach()
        }
    }
}