package com.icsd17029.earthquakeproject.internal.models

data class FirstGradeCheckModel(
        var id: String = "",
        var buildingID: String = "",
        var date: String = "",
        var userID: String = "",
        var viable: Boolean = true
)
