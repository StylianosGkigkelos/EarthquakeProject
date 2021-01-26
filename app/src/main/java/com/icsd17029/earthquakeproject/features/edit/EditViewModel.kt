package com.icsd17029.earthquakeproject.features.edit

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.internal.models.BuildingModel
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.OwnerModel

class EditViewModel: ViewModel() {
    // Firestore
    private val db = Firebase.firestore

    fun updateInfo(owner: OwnerModel, building: BuildingModel, check: FirstGradeCheckModel){
        val docRef = db.collection("Owner").document(owner.id)
        val ownerUpdates = hashMapOf<String, Any>(
                "name" to owner.name,
                "surname" to owner.surname,
                "phone" to owner.phone
        )
        docRef.update(ownerUpdates).addOnSuccessListener {
            Log.d(TAG,"Owner successfully updated")
            val buildingRef = db.collection("Building").document(building.id)
            val buildingUpdates = hashMapOf<String, Any>(
                            "name" to building.name,
                            "municipality" to building.municipality,
                            "street" to building.street,
                            "number" to building.number,
                            "area" to building.area,
                            "noOfStoreys" to building.noOfStoreys,
                            "noOfApartments" to building.noOfApartments,
                            "enum" to building.enum
            )
            buildingRef.update(buildingUpdates).addOnSuccessListener {
                Log.d(TAG,"Building successfully updated")
                val checkRef = db.collection("FirstGradeCheck").document(check.id)
                checkRef.update("viable", check.viable).addOnSuccessListener {
                    Log.d(TAG, "First grade check successfully updated.")
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating check", e) }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating building ", e) }
        }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating owner ", e) }
    }
}