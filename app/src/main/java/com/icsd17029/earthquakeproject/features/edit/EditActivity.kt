package com.icsd17029.earthquakeproject.features.edit

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.ViewModelProvider
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.features.add.AddViewModel
import com.icsd17029.earthquakeproject.features.list.ListsActivity
import com.icsd17029.earthquakeproject.features.map.MapActivity
import com.icsd17029.earthquakeproject.internal.enumerators.BuildingEnum
import com.icsd17029.earthquakeproject.internal.models.BuildingModel
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.OwnerModel
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*

class EditActivity: AppCompatActivity(){
    //TODO receive data
//    private val viewModel: EditViewModel by lazy {
//        ViewModelProvider(this).get(EditViewModel::class.java)
//    }
//
//    lateinit var spinner: AppCompatSpinner
//
//    var pos: String = ""
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add)
//
//        spinner = buildingEnum
//        add_submit.setOnClickListener {
//            //viewModel.submitTest()
//            if(validateForm()){
//                val owner = OwnerModel("", ownerName.editText?.text.toString(),
//                        ownerSurname.editText?.text.toString(), ownerPhone.editText?.text.toString())
//
//                val building = BuildingModel("", buildingName.editText?.text.toString(),
//                        21.2, 21.1,
//                        buildingMunicipality.editText?.text.toString(),
//                        buildingStreet.editText?.text.toString(),
//                        buildingNumber.editText?.text.toString(),
//                        buildingArea.editText?.text.toString(),
//                        "", buildingStorey.editText?.text.toString(),
//                        buildingApartment.editText?.text.toString(), BuildingEnum.valueOf(pos)
//                )
//                val date = Calendar.getInstance().time.toString()
//
//                val check = FirstGradeCheckModel(
//                        "","", date, "", add_isViable.isChecked
//                )
//
//                viewModel.submit(owner, building, check)
//            }
//
//            Log.d(ContentValues.TAG, "Checkbox ${add_isViable.isChecked}")
//            Log.d(ContentValues.TAG, "Item selected = $pos")
//        }
//        bottom_nav.selectedItemId = R.id.navigation_add
//        bottom_nav.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.navigation_map -> {
//                    startActivity(Intent(this, MapActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    finish()
//                }
//                R.id.navigation_list -> {
//                    startActivity(Intent(this, ListsActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    finish()
//                }
//            }
//            true
//        }
//
//
//        ArrayAdapter.createFromResource(
//                this,
//                R.array.building_array,
//                android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }
//        spinner.onItemSelectedListener = this
//
//
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        Log.d(ContentValues.TAG, "Nothing is Selected")
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        Log.d(ContentValues.TAG, "OnItemSelected")
//        if (parent != null) {
//            pos = parent.getItemAtPosition(position).toString()
//        }
//    }
//
//    private fun validateForm(): Boolean {
//        var valid = true
//        if (TextUtils.isEmpty(ownerName.editText?.text.toString())) {
//            valid = false
//            ownerName.error = "Required"
//        } else {
//            ownerName.error = null
//        }
//
//        if (TextUtils.isEmpty(ownerSurname.editText?.text.toString())) {
//            valid = false
//            ownerSurname.error = "Required"
//        } else {
//            ownerSurname.error = null
//        }
//
//        if (TextUtils.isEmpty(ownerPhone.editText?.text.toString())) {
//            valid = false
//            ownerPhone.error = "Required"
//        } else {
//            ownerPhone.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingName.editText?.text.toString())) {
//            valid = false
//            buildingName.error = "Required"
//        } else {
//            buildingName.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingMunicipality.editText?.text.toString())) {
//            valid = false
//            buildingMunicipality.error = "Required"
//        } else {
//            buildingMunicipality.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingStreet.editText?.text.toString())) {
//            valid = false
//            buildingStreet.error = "Required"
//        } else {
//            ownerSurname.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingName.editText?.text.toString())) {
//            valid = false
//            buildingName.error = "Required"
//        } else {
//            ownerSurname.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingArea.editText?.text.toString())) {
//            valid = false
//            buildingArea.error = "Required"
//        } else {
//            ownerSurname.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingStorey.editText?.text.toString())) {
//            valid = false
//            buildingStorey.error = "Required"
//        } else if (!TextUtils.isDigitsOnly(buildingStorey.editText?.text.toString())) {
//            valid = false
//            buildingStorey.error = "Enter a Number"
//        }
//        else {
//            ownerSurname.error = null
//        }
//
//        if (TextUtils.isEmpty(buildingApartment.editText?.text.toString())) {
//            valid = false
//            buildingApartment.error = "Required"
//        }
//        else if (!TextUtils.isDigitsOnly(buildingApartment.editText?.text.toString())) {
//            valid = false
//            buildingApartment.error = "Enter a Number"
//        }
//        else {
//            ownerSurname.error = null
//        }
//
//
//        return valid
//    }
}

