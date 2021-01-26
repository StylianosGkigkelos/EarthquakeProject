package com.icsd17029.earthquakeproject.features.add

import android.content.ContentValues.TAG
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.features.list.ListsActivity
import com.icsd17029.earthquakeproject.features.map.MapActivity
import com.icsd17029.earthquakeproject.internal.enumerators.BuildingEnum
import com.icsd17029.earthquakeproject.internal.enumerators.SGUsage
import com.icsd17029.earthquakeproject.internal.models.BuildingModel
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import com.icsd17029.earthquakeproject.internal.models.OwnerModel
import com.icsd17029.earthquakeproject.internal.models.SecondGradeCheckModel
import kotlinx.android.synthetic.main.activity_add.*
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private val viewModel: AddViewModel by lazy {
        ViewModelProvider(this).get(AddViewModel::class.java)
    }

    lateinit var spinner: AppCompatSpinner
    lateinit var spinner2: AppCompatSpinner


    var pos: String = ""
    var pos2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        setContentView(R.layout.activity_add)

        spinner = buildingEnum
        spinner2 = sg_enum
        add_submit.setOnClickListener {
            //viewModel.submitTest()
            if(validateForm()){
                val owner = OwnerModel("", ownerName.editText?.text.toString(),
                        ownerSurname.editText?.text.toString(), ownerPhone.editText?.text.toString())

                val building = BuildingModel("", buildingName.editText?.text.toString(),
                        21.2, 21.1,
                        buildingMunicipality.editText?.text.toString(),
                        buildingStreet.editText?.text.toString(),
                        buildingNumber.editText?.text.toString(),
                        buildingArea.editText?.text.toString(),
                        "", buildingStorey.editText?.text.toString(),
                        buildingApartment.editText?.text.toString(), BuildingEnum.valueOf(pos)
                )
                val date = Calendar.getInstance().time.toString()

                val check = FirstGradeCheckModel(
                        "","", date, auth.currentUser?.uid.toString(), add_isViable.isChecked
                )
                if(TextUtils.isEmpty(sg_desc.editText?.text.toString())){
                    viewModel.submit(owner, building, check)
                } else if(pos2 != "Condition of the building during second grade check"){
                    val sgCheck = SecondGradeCheckModel("", "", date,
                            sg_desc.editText?.text.toString(), auth.currentUser?.uid.toString(), SGUsage.valueOf(pos2) )
                    viewModel.submit(owner, building, check, sgCheck)
                }
            }

            Log.d(TAG, "Checkbox ${add_isViable.isChecked}")
            Log.d(TAG, "Item selected = $pos")
        }
        bottom_nav.selectedItemId = R.id.navigation_add
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
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


        ArrayAdapter.createFromResource(
                this,
                R.array.building_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this


        ArrayAdapter.createFromResource(
                this,
                R.array.check_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }
        spinner2.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG, "Nothing is Selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "OnItemSelected")
        if (parent != null && (parent.id == buildingEnum.id)) {
            Log.d(TAG, "First")
            pos = parent.getItemAtPosition(position).toString()
        } else if( parent!= null && (parent.id == sg_enum.id)){
            pos2 = parent.getItemAtPosition(position).toString()
            Log.d(TAG, "Second")
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        if (TextUtils.isEmpty(ownerName.editText?.text.toString())) {
            valid = false
            ownerName.error = "Required"
        } else {
            ownerName.error = null
        }

        if (TextUtils.isEmpty(ownerSurname.editText?.text.toString())) {
            valid = false
            ownerSurname.error = "Required"
        } else {
            ownerSurname.error = null
        }

        if (TextUtils.isEmpty(ownerPhone.editText?.text.toString())) {
            valid = false
            ownerPhone.error = "Required"
        } else {
            ownerPhone.error = null
        }

        if (TextUtils.isEmpty(buildingName.editText?.text.toString())) {
            valid = false
            buildingName.error = "Required"
        } else {
            buildingName.error = null
        }

        if (TextUtils.isEmpty(buildingMunicipality.editText?.text.toString())) {
            valid = false
            buildingMunicipality.error = "Required"
        } else {
            buildingMunicipality.error = null
        }

        if (TextUtils.isEmpty(buildingStreet.editText?.text.toString())) {
            valid = false
            buildingStreet.error = "Required"
        } else {
            ownerSurname.error = null
        }

        if (TextUtils.isEmpty(buildingName.editText?.text.toString())) {
            valid = false
            buildingName.error = "Required"
        } else {
            ownerSurname.error = null
        }

        if (TextUtils.isEmpty(buildingArea.editText?.text.toString())) {
            valid = false
            buildingArea.error = "Required"
        } else {
            ownerSurname.error = null
        }

        if (TextUtils.isEmpty(buildingStorey.editText?.text.toString())) {
            valid = false
            buildingStorey.error = "Required"
        } else if (!TextUtils.isDigitsOnly(buildingStorey.editText?.text.toString())) {
            valid = false
            buildingStorey.error = "Enter a Number"
        }
        else {
            ownerSurname.error = null
        }

        if (TextUtils.isEmpty(buildingApartment.editText?.text.toString())) {
            valid = false
            buildingApartment.error = "Required"
        }
        else if (!TextUtils.isDigitsOnly(buildingApartment.editText?.text.toString())) {
            valid = false
            buildingApartment.error = "Enter a Number"
        }
        else {
                ownerSurname.error = null
        }


        return valid
    }
}
