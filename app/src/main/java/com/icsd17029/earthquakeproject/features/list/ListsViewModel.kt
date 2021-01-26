package com.icsd17029.earthquakeproject.features.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.internal.enumerators.SGUsage
import com.icsd17029.earthquakeproject.internal.models.BuildingModel
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.SecondGradeCheckModel

class ListsViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val _fgResponse = MutableLiveData<List<FirstGradeCheckModel>>()
    val responseGetFG: LiveData<List<FirstGradeCheckModel>>
        get() = _fgResponse

    private val  _sgResponse = MutableLiveData<List<SecondGradeCheckModel>>()
    val responseGetSG: LiveData<List<SecondGradeCheckModel>>
        get() = _sgResponse


    init {
        getFirstGradeChecks()
        getSecondGradeChecks()
    }

    private fun getFirstGradeChecks() {
        val eqList = mutableListOf<FirstGradeCheckModel>()
        db.collection("FirstGradeCheck").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        eqList.add(document.toObject())
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    _fgResponse.value = eqList
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }


    }

    private fun getSecondGradeChecks() {
        val eqList = mutableListOf<SecondGradeCheckModel>()
        db.collection("SecondGradeCheck").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        eqList.add(document.toObject())
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        _sgResponse.value = eqList
    }

    fun matchFirstWithSecond(secondID: String): FirstGradeCheckModel? {
        for(check in _fgResponse.value!!){
            if(secondID == check.id){
                return check;
            }
        }
        return null
    }

    fun getBuildingFromFirst(id: String){
        var building: BuildingModel
//        db.collection("Building").whereEqualTo("id", id).get().addOnSuccessListener {
//
//        }

    }

}