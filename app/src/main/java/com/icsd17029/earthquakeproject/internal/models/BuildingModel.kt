package com.icsd17029.earthquakeproject.internal.models

import com.icsd17029.earthquakeproject.internal.enumerators.BuildingEnum

data class BuildingModel (
    var id: String = "",
    var name: String = "",
    var longitude: Double = 20.0,
    var latitude: Double = 20.0,
    var municipality: String = "",
    var street: String = "",
    var number: String = "",
    var area: String = "",
    var ownerID: String = "",
    var noOfStoreys: String = "",
    var noOfApartments: String = "",
    val enum: BuildingEnum = BuildingEnum.INUSE

)