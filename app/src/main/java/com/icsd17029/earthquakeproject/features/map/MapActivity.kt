package com.icsd17029.earthquakeproject.features.map

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.features.add.AddActivity
import com.icsd17029.earthquakeproject.features.list.ListsActivity
import com.icsd17029.earthquakeproject.internal.enumerators.SGUsage
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private val DEFAULT_ZOOM = 10
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private val defaultLocation = LatLng(37.9358300, 22.94598937)

    private lateinit var mMap: GoogleMap
    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(this).get(MapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        bottom_nav.selectedItemId = R.id.navigation_map
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_add -> {
                    startActivity(Intent(this, AddActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.navigation_list -> {
                    startActivity(Intent(this, ListsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
            }
            true


        }

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()


        viewModel.responseGetFG.observe(this, {
            for(fg in it) {
                Log.d(TAG, "I am a map")
                viewModel.getBuildingFromID(fg.buildingID)
                viewModel.responseGetBuilding.observe(this, {
                    val markerPosition = LatLng(viewModel.responseGetBuilding.value!!.latitude
                        , viewModel.responseGetBuilding.value!!.longitude)

                    if(fg.viable) {
                        val icon = BitmapDescriptorFactory.
                        fromBitmap(resources.getDrawable(R.drawable.ic_baseline_greencross_24, null)
                                .toBitmap(width = 100, height=100))

                        mMap.addMarker(MarkerOptions().icon(icon).position(markerPosition).title(
                                viewModel.responseGetBuilding.value!!.name))
                    } else {
                        val icon = BitmapDescriptorFactory.
                        fromBitmap(resources.getDrawable(R.drawable.ic_baseline_yellowcross_24, null)
                                .toBitmap(width = 100, height=100))


                        mMap.addMarker(MarkerOptions().icon(icon).position(markerPosition).title(
                                viewModel.responseGetBuilding.value!!.name))
                    }
                })


            }
        })

        viewModel.responseGetSG.observe(this, {
            for(sg in it){
                viewModel.getSGBuildingFromID(sg.firstGradeCheckID)

                viewModel.responseGetBuildingSG.observe(this, {
                    val markerPosition = LatLng(viewModel.responseGetBuildingSG.value!!.latitude
                            , viewModel.responseGetBuildingSG.value!!.longitude)

                    when(sg.enum){
                        SGUsage.HOSPITABLE -> {
                            val icon = BitmapDescriptorFactory.
                            fromBitmap(resources.getDrawable(R.drawable.ic_baseline_greencross_24, null)
                                    .toBitmap(width = 100, height=100))

                            mMap.addMarker(MarkerOptions()
                                    .icon(icon).
                                    position(markerPosition).title(
                                            viewModel.responseGetBuildingSG.value!!.name))
                        }

                        SGUsage.TEMPUNHOSPITABLE -> {
                            val icon = BitmapDescriptorFactory.
                            fromBitmap(resources.getDrawable(R.drawable.ic_baseline_triangleorange_24, null)
                                    .toBitmap(width = 100, height=100))

                            mMap.addMarker(MarkerOptions()
                                    .icon(icon).
                                    position(markerPosition).title(
                                            viewModel.responseGetBuildingSG.value!!.name))
                        }

                        SGUsage.UNHOSPITABLE -> {
                            val icon = BitmapDescriptorFactory.
                            fromBitmap(resources.getDrawable(R.drawable.ic_baseline_trianglered_24, null)
                                    .toBitmap(width = 100, height=100))

                            mMap.addMarker(MarkerOptions()
                                    .icon(icon).
                                    position(markerPosition).title(
                                            viewModel.responseGetBuildingSG.value!!.name))
                        }
                    }

                })
            }
        })

    }


    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation

                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        //Log.d(TAG, "Current location is null. Using defaults.")
//                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap?.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


}
