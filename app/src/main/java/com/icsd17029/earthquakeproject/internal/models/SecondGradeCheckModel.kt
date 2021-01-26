package com.icsd17029.earthquakeproject.internal.models

import com.icsd17029.earthquakeproject.internal.enumerators.SGUsage
import java.util.*

data class SecondGradeCheckModel(
        var id: String = "",
        var firstGradeCheckID: String = "",
        val date: String = "",
        val description: String = "",
        val userID: String = "",
        val enum: SGUsage = SGUsage.HOSPITABLE

)
