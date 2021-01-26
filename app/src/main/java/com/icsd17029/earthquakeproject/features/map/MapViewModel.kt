package com.icsd17029.earthquakeproject.features.map

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.internal.models.BuildingModel
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.SecondGradeCheckModel
import kotlinx.coroutines.launch

class MapViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val _fgResponse = MutableLiveData<List<FirstGradeCheckModel>>()
    val responseGetFG: LiveData<List<FirstGradeCheckModel>>
        get() = _fgResponse

    private val  _sgResponse = MutableLiveData<List<SecondGradeCheckModel>>()
    val responseGetSG: LiveData<List<SecondGradeCheckModel>>
        get() = _sgResponse

    private val _building = MutableLiveData<BuildingModel>()
    val responseGetBuilding: LiveData<BuildingModel>
        get() = _building

    private val _buildingSG = MutableLiveData<BuildingModel>()
    val responseGetBuildingSG: LiveData<BuildingModel>
        get() = _buildingSG

    init {
        getChecks()
    }

    private fun getChecks() {
        viewModelScope.launch {
            val fgList = mutableListOf<FirstGradeCheckModel>()
            val sgList = mutableListOf<SecondGradeCheckModel>()

            db.collection("FirstGradeCheck").get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            fgList.add(document.toObject())
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        }
                        _fgResponse.value = fgList
                        Log.d(ContentValues.TAG, _fgResponse.value.toString())
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }


            db.collection("SecondGradeCheck").get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {

                            sgList.add(document.toObject())
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        }
                        _sgResponse.value = sgList
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }

        }
    }

    fun getBuildingFromID(buildingID: String) {
        db.collection("Building").document(buildingID).get()
                .addOnSuccessListener { document ->
                    _building.value = document.toObject<BuildingModel>()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
    }

    fun getSGBuildingFromID(buildingID: String) {
        db.collection("Building").document(buildingID).get()
                .addOnSuccessListener { document ->
                    _buildingSG.value = document.toObject<BuildingModel>()
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
    }

}