package ir.androidcoder.hammasir.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Rect
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.androidcoder.hammasir.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class MapViewModel @Inject constructor(private val context: Context) : ViewModel() {

    lateinit var mMap: MapView
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay

    fun initializeMap(mapView: MapView) {

        Configuration.getInstance().load(context, context.getSharedPreferences(context.getString(R.string.app_name), Application.MODE_PRIVATE))

        mMap = mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            getLocalVisibleRect(Rect())
        }

        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(mMap.context), mMap).apply {
            enableMyLocation()
            enableFollowLocation()
            isDrawAccuracyEnabled = true
            runOnFirstFix {
                mMap.controller.setCenter(myLocation)
                mMap.controller.animateTo(myLocation)
            }
        }


    }

    fun centerMapAt(location: GeoPoint, zoomLevel: Double) {
        mMap.controller.setZoom(zoomLevel)
        mMap.controller.setCenter(location)
        addMarker(location)
    }

    private fun addMarker(location: GeoPoint) {
        val marker = Marker(mMap).apply {
            position = location
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        mMap.overlays.add(marker)
    }

}