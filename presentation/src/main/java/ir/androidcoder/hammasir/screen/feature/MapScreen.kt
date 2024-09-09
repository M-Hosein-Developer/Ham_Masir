@file:Suppress("NAME_SHADOWING")

package ir.androidcoder.hammasir.screen.feature

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.hammasir.R
import ir.androidcoder.hammasir.util.MyScreen
import ir.androidcoder.hammasir.util.locationDataEmpty
import ir.androidcoder.hammasir.viewModel.MapViewModel
import ir.androidcoder.hammasir.viewModel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay

@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    lat: String?,
    long: String?,
    name: String?,
    ) {

    val context = LocalContext.current


    Column(
        Modifier.fillMaxSize()
    ) {

        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
        ) {

            MapSetting(mapViewModel, searchViewModel , GeoPoint(lat?.toDouble() ?: 0.0 , long?.toDouble() ?: 0.0) , name)
            LocationButtonSetting {
                mapViewModel.getUserLocation(context) { lat, long ->
                    mapViewModel.centerMapAt(GeoPoint(lat, long), 18.0)
                    mapViewModel.setInitialMarker(GeoPoint(lat, long))
                }

            }

            SearchLocation{
                navController.navigate(MyScreen.SearchScreen.route)
            }


        }


    }

}

@Composable
fun MapSetting(
    mapViewModel: MapViewModel,
    searchViewModel: SearchViewModel,
    searchData: GeoPoint,
    name: String?
) {

    //variables
    val context = LocalContext.current
    var hasLocationPermission by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val userLocation = remember { mutableStateOf(Pair(51.131, 12.414)) }
    val dialog = remember { mutableStateOf(false) }
    val homeAndWorkPoint = remember { mutableStateOf(GeoPoint(51.131, 12.414)) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val onSingleTapPoint = remember { mutableStateOf(GeoPoint(51.131, 12.414)) }
    val locationData = remember { mutableStateOf(locationDataEmpty.value) }


    //permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                hasLocationPermission = true
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    //map
    if (hasLocationPermission) {
        AndroidView(factory = { context ->
            MapView(context).also { mapView ->
                mapViewModel.initializeMap(mapView)
                coroutineScope.launch {
                    mapViewModel.getUserLocation(context) { lat, long ->
                        userLocation.value = Pair(lat, long)

                        if (searchData.latitude == 0.0) {
                            mapViewModel.centerMapAt(GeoPoint(lat, long), 18.0)
                        }else {
                            mapViewModel.centerMapAt(searchData, 18.0)
                            mapViewModel.addMarkerClicked(searchData , name ?: "")
                        }

                        mapViewModel.setInitialMarker(GeoPoint(lat, long))

                        SetupLocationIcon(searchData , mapViewModel , userLocation.value , name)
                    }


                    mapView.overlays.add(object : Overlay() {
                        override fun draw(c: Canvas?, osmv: MapView?, shadow: Boolean) {

                        }

                        override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {

                            e?.let {
                                val point = mapView?.projection?.fromPixels(
                                    e.x.toInt(), e.y.toInt()
                                ) as GeoPoint

                                mapViewModel.getLocationData(
                                GeoPoint(userLocation.value.first, userLocation.value.second),
                                onSingleTapPoint.value
                                )

                                coroutineScope.launch {
                                    mapViewModel.locationData.collect{
                                        locationData.value = it ?: locationDataEmpty.value
                                    }
                                }

                                onSingleTapPoint.value = point
                                showBottomSheet.value = true

                            }
                            return true
                        }

                        override fun onLongPress(e: MotionEvent?, mapView: MapView?): Boolean {

                            e?.let {
                                val point = mapView?.projection?.fromPixels(
                                    e.x.toInt(), e.y.toInt()
                                ) as GeoPoint

                                dialog.value = true
                                homeAndWorkPoint.value = point

                            }


                            return super.onLongPress(e, mapView)
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
                                e: MotionEvent?, mapView: MapView?
                            ): Boolean {
                                e?.let {
                                    val point = mapView?.projection?.fromPixels(
                                        e.x.toInt(), e.y.toInt()
                                    ) as GeoPoint
                                    mapViewModel.addMarkerClicked(point , "")
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 42.dp)
            )

        }

    }

    //dialog
    AlertDialogLocation(
        openDialog = dialog.value,
        onHomeClicked = {
            searchViewModel.insertHomeLocation(
                HomeEntity(
                    0,
                    homeAndWorkPoint.value.latitude,
                    homeAndWorkPoint.value.longitude,
                    "خانه",
                    "",
                    ""
                )
            )
            mapViewModel.addHomeMarkerClicked(homeAndWorkPoint.value)
            dialog.value = it
        },
        onWorkClicked = {
            searchViewModel.insertWorkLocation(
                WorkEntity(
                    0,
                    homeAndWorkPoint.value.latitude,
                    homeAndWorkPoint.value.longitude,
                    "محل کار",
                    "",
                    ""
                )
            )
            mapViewModel.addWorkMarkerClicked(homeAndWorkPoint.value)
            dialog.value = it
        },
        dismiss = { dialog.value = it }
    )

    //BottomSheet
    BottomSheet(
        streetName = if (
            locationData.value.paths[0].instructions[locationData.value.paths[0].instructions.lastIndex].street_name != null &&
            locationData.value.paths[0].instructions[locationData.value.paths[0].instructions.lastIndex].street_name != ""
            )
            locationData.value.paths[0].instructions[locationData.value.paths[0].instructions.lastIndex].street_name
        else
            "نامی یافت نشد",
        distance = locationData.value.paths[0].distance.toString(),
        showBottomSheet = showBottomSheet.value,
        onShowBottomSheet = { showBottomSheet.value = it }) {

        mapViewModel.addMarkerClicked(onSingleTapPoint.value , name ?: "نامی یافت نشد")

        coroutineScope.launch {

            while (true) {

                    mapViewModel.drawManualRoute(
                        GeoPoint(userLocation.value.first, userLocation.value.second),
                        onSingleTapPoint.value
                    )
                    mapViewModel.centerMapAt(GeoPoint(userLocation.value.first, userLocation.value.second), 18.0)

                delay(20000)
            }

        }

        showBottomSheet.value = false

    }

    DisposableEffect(Unit) {
        onDispose {
            mapViewModel.mMap.onDetach()
        }
    }
}


@Composable
fun LocationButtonSetting(locationButtonSetting: () -> Unit) {

    FloatingActionButton(
        onClick = { locationButtonSetting.invoke() },
        modifier = Modifier.padding(end = 24.dp, bottom = 130.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.location), contentDescription = null)
    }

}

@Composable
fun SearchLocation(onSearchClicked :() -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(topEnd = 18.dp, topStart = 18.dp))
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .size(52.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(32.dp))
        )

        TextButton(
            onClick = { onSearchClicked.invoke() },
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(vertical = 14.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(32.dp))
                .fillMaxWidth(),
        ) {
            Text(
                text = "کجا میخوای بری؟" ,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) ,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
        }

    }

}

@Composable
fun AlertDialogLocation(openDialog: Boolean, onHomeClicked: (Boolean) -> Unit, onWorkClicked: (Boolean) -> Unit , dismiss: (Boolean) -> Unit) {


    if (openDialog) {

        AlertDialog(
            onDismissRequest = {
                dismiss.invoke(false)
            },
            title = {
                Text(
                    text = "انتخاب مکان جدید",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            text = {
                Text(
                    "مکانی که انتخاب کردی برای خونست یا محل کارت؟",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            confirmButton = {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        onClick = {
                            onWorkClicked.invoke(false)
                        },
                        Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(4.dp)
                    ) {
                        Text("محل کار")
                    }

                    Button(
                        onClick = {
                            onHomeClicked.invoke(false)
                        },
                        Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(4.dp)
                        ) {
                        Text("خونه")
                    }
                }
            },
//            dismissButton = {
//                Button(
//
//                    onClick = {
//                        openDialog.value = false
//                    }) {
//                    Text("Dismiss Button")
//                }
//            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    streetName: String,
    distance: String,
    showBottomSheet: Boolean,
    onShowBottomSheet: (Boolean) -> Unit,
    onRoutingClicked: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onShowBottomSheet.invoke(false)
            },
            sheetState = sheetState
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = streetName,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)

                )

                Text(
                    text = distance,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding()
                )


                Button(
                    onClick = {

                        onRoutingClicked.invoke()

                    },
                    Modifier
                        .padding(vertical = 16.dp)

                ) {
                    Text("بزن بریم")
                    Icon(painter = painterResource(R.drawable.routing_button) , contentDescription = null)
                }

            }



        }
    }

}

//--------------------------------------------------------------------------------------------------
fun SetupLocationIcon(
    homeWorkPoint: GeoPoint,
    mapViewModel: MapViewModel,
    userLocation: Pair<Double, Double>,
    name: String?
) {

    if (homeWorkPoint.latitude != 0.0) {

        when(name){

            "خانه" ->{
                mapViewModel.addHomeMarkerClicked(homeWorkPoint)
            }
            "محل کار" ->{
                mapViewModel.addWorkMarkerClicked(homeWorkPoint)
            }
            else -> {
                mapViewModel.addMarkerClicked(homeWorkPoint , name ?: "")
            }

        }

        mapViewModel.drawManualRoute(
            GeoPoint(userLocation.first, userLocation.second),
            GeoPoint(homeWorkPoint.latitude, homeWorkPoint.longitude)
        )

    }

}