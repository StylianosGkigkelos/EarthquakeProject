package com.icsd17029.earthquakeproject.internal.models

data class UserModel(
        val uid: String? = "",
        val name: String = "",
        val surname: String = "",
        val civilEngineer: Boolean = false
)