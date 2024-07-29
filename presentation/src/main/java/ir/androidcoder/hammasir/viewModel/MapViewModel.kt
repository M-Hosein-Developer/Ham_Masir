package ir.androidcoder.hammasir.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private var clickMarker: Marker? = null
    private var initialMarker: Marker? = null
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    //---Init Map-----------------------------------------------------------------------------------
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
                val myLocation = myLocation
                mMap.controller.setCenter(myLocation)
                setInitialMarker(myLocation)
            }
        }
    }

    fun centerMapAt(location: GeoPoint, zoomLevel: Double) {
        mMap.controller.setZoom(zoomLevel)
        mMap.controller.setCenter(location)
        setInitialMarker(location)
    }


    //---Add Marker By Click------------------------------------------------------------------------
    private fun setInitialMarker(location: GeoPoint) {
        if (initialMarker == null) {
            initialMarker = Marker(mMap).apply {
                position = location
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = resizeDrawable(R.drawable.now_locat, 60, 60)
                mMap.overlays.add(this)
            }
        } else {
            initialMarker?.position = location
        }
        mMap.invalidate()
    }

    fun addMarkerClicked(point: GeoPoint) {
        if (clickMarker == null) {
            clickMarker = Marker(mMap).apply {
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = resizeDrawable(R.drawable.select_locat, 60, 60)
                mMap.overlays.add(this)
            }
        }
        clickMarker?.position = point
        mMap.invalidate()
    }

    //---Icon Size----------------------------------------------------------------------------------
    private fun resizeDrawable(drawableRes: Int, width: Int, height: Int): Drawable {
        val drawable = ContextCompat.getDrawable(context, drawableRes)
        val bitmap = drawableToBitmap(drawable)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDrawable(context.resources, resizedBitmap)
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    //---Location-----------------------------------------------------------------------------------
    fun getUserLocation(context: Context) : Pair<Double , Double> {

        var getLocation = Pair(0.0 , 0.0)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    getLocation = Pair(it.latitude , it.longitude)
                }
            }
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        return getLocation
    }

}
