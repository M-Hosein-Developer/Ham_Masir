package ir.androidcoder.hammasir.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.androidcoder.domain.entities.RouteEntity
import ir.androidcoder.domain.useCase.road.RoadUsecase
import ir.androidcoder.hammasir.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class MapViewModel @Inject constructor(private val context: Context , private val roadUsecase: RoadUsecase) : ViewModel() {

    lateinit var mMap: MapView
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay
    private var clickMarker: Marker? = null
    private var HomeMarker: Marker? = null
    private var initialMarker: Marker? = null

    //---Location Data------------------------------------------------------------------------------
    private val _locationData = MutableStateFlow<RouteEntity?>(null)
    val locationData : StateFlow<RouteEntity?> get() = _locationData
    

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

        viewModelScope.launch {
            delay(3000)
            setInitialMarker(location)
        }

    }


    //---Add Marker By Click------------------------------------------------------------------------
    fun setInitialMarker(location: GeoPoint) {
        if (initialMarker == null) {
            initialMarker = Marker(mMap).apply {
                position = location
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                icon = resizeDrawable(R.drawable.now_location, 120, 120)
                mMap.overlays.add(this)
                mMap.invalidate()
            }
        } else {
            initialMarker?.position = location
            mMap.invalidate()
        }
    }

    fun addMarkerClicked(point: GeoPoint) {
        if (clickMarker == null) {
            clickMarker = Marker(mMap).apply {
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = resizeDrawable(R.drawable.select_locat, 120, 120)
                mMap.overlays.add(this)
            }
        }
        clickMarker?.position = point
        mMap.invalidate()
    }

    fun addHomeMarkerClicked(point: GeoPoint) {
        if (HomeMarker == null) {
            HomeMarker = Marker(mMap).apply {
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon = resizeDrawable(R.drawable.home_location, 120, 120)
                mMap.overlays.add(this)
            }
        }
        HomeMarker?.position = point
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
    fun getUserLocation(context: Context, callback: (Double, Double) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    callback(latitude, longitude)
                    viewModelScope.launch {
                        while (true) {
                            setInitialMarker(GeoPoint(latitude, longitude))
                            delay(4000)
                        }
                    }
                } ?: run {
                    Log.e("locat1", "Location is null")
                    callback(51.131, 12.414)
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            Log.e("locat1", "Permission not granted")
            callback(51.131, 12.414)
        }
    }


    //---Draw Route---------------------------------------------------------------------------------
    fun drawManualRoute(startPoint: GeoPoint, endPoint: GeoPoint) {

        val start = startPoint.latitude.toString() + "," + startPoint.longitude.toString()
        val end = endPoint.latitude.toString() + "," + endPoint.longitude.toString()

        Log.v("locat" , start)
        Log.v("locat" , end)

        viewModelScope.launch {

            roadUsecase.getRoute(start , end)
                .catch {
                    it.localizedMessage?.let { it1 -> Log.e("ApiError" , it1) }
                }
                .collect{

                val routePoints = mutableListOf<GeoPoint>()
                it.paths.forEach { path ->
                    val decodedPoints = decodePolyline(path.points)
                    routePoints.addAll(decodedPoints)
                }
                drawRoute(routePoints)
            }

        }

    }

    private fun decodePolyline(encoded: String, precision: Double = 1E5): List<GeoPoint> {
        val poly = ArrayList<GeoPoint>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val geoPoint = GeoPoint(lat / precision, lng / precision)
            poly.add(geoPoint)
        }

        return poly
    }

    private fun drawRoute(routePoints: List<GeoPoint>) {

        val line = Polyline().apply {
            setPoints(routePoints)
            outlinePaint.color = Color.CYAN
            outlinePaint.strokeWidth = 16.0f
        }
        mMap.overlayManager.add(line)
        mMap.invalidate()
    }

    fun getLocationData(startPoint: GeoPoint, endPoint: GeoPoint) = viewModelScope.launch {

        val start = startPoint.latitude.toString() + "," + startPoint.longitude.toString()
        val end = endPoint.latitude.toString() + "," + endPoint.longitude.toString()

        roadUsecase.getRoute(start , end)
            .catch {
                it.localizedMessage?.let { it1 -> Log.e("ApiError" , it1) }
            }
            .collect{

                _locationData.value = it

            }

    }

}
