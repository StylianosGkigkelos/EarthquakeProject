package com.icsd17029.earthquakeproject.features.add

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.location.FusedLocationProviderClient
import com.icsd17029.earthquakeproject.internal.enumerators.BuildingEnum
import com.icsd17029.earthquakeproject.internal.enumerators.SGUsage
import com.icsd17029.earthquakeproject.internal.models.BuildingModel

import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.OwnerModel
import com.icsd17029.earthquakeproject.internal.models.SecondGradeCheckModel

class AddViewModel: ViewModel() {

    // Firestore
    private val db = Firebase.firestore

    // Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private val userLocation = LatLng(37.9358300, 22.94598937)

//region
//    private fun getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                val locationResult = fusedLocationProviderClient.lastLocation
//
//                locationResult.addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Set the map's camera position to the current location of the device.
//                        lastKnownLocation = task.result
//                        if (lastKnownLocation != null) {
//
//                        }
//                    }
//                }
//            }
//        } catch (e: SecurityException) {
//            Log.e("Exception: %s", e.message, e)
//        }
//    }
//endregion

    fun submitFirstGradeCheck(check: FirstGradeCheckModel) {
        val ref = db.collection("FirstGradeCheck").document().id
        check.id = ref
        db.collection("FirstGradeCheck").add(check)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun submitBuilding(building: BuildingModel) {
        val ref = db.collection("Building").document().id
        building.id = ref
        db.collection("Building").add(building)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
    
    fun submitOwner(owner: OwnerModel){
        val ref = db.collection("Owner").document().id
        owner.id = ref
        db.collection("Owner").add(owner)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun submit(owner: OwnerModel, building: BuildingModel, check: FirstGradeCheckModel) {
        var ref = db.collection("Owner").document()
        owner.id = ref.id
        ref.set(owner)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot (Owner) successfully written!")
                    building.ownerID = owner.id
                    ref = db.collection("Building").document()
                    building.id = ref.id
                    ref.set(building)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot (Building) successfully written!")
                                check.buildingID = building.id
                                ref = db.collection("FirstGradeCheck").document()
                                check.id = ref.id
                                ref.set(check)
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot (Check) successfully written!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun submit(owner: OwnerModel, building: BuildingModel, check: FirstGradeCheckModel, sgCheck: SecondGradeCheckModel){
        var ref = db.collection("Owner").document()
        owner.id = ref.id
        ref.set(owner)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot (Owner) successfully written!")
                    building.ownerID = owner.id
                    ref = db.collection("Building").document()
                    building.id = ref.id
                    ref.set(building)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot (Building) successfully written!")
                                check.buildingID = building.id
                                ref = db.collection("FirstGradeCheck").document()
                                check.id = ref.id
                                ref.set(check)
                                        .addOnSuccessListener {
                                            Log.d(TAG, "DocumentSnapshot (Check) successfully written!")
                                            ref = db.collection("SecondGradeCheck").document()
                                            sgCheck.id = ref.id
                                            sgCheck.firstGradeCheckID = check.id
                                            ref.set(sgCheck).addOnSuccessListener {
                                                Log.d(TAG, "DocumentSnapshot (Second Grade Check) successfully written!")
                                            }
                                            .addOnFailureListener {
                                                e -> Log.w(TAG, "Error writing document (Second Grade)", e)
                                            }
                                        }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }

    fun submitTest() {
        val owner = OwnerModel("","Asdf","Qwerty", "210-000123")
        val building = BuildingModel("","Building Name", 23.0002, 25.211,
                "Korinthos", "Zallogou", "13", "Synoikismos", "",
        "2","3", BuildingEnum.INUSE)
        val check = FirstGradeCheckModel("", "1","2001-01-12",
                "asdf",  true)
        submit(owner, building, check)

    }


}