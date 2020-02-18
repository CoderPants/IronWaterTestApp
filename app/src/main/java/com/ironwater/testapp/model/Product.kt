package com.ironwater.testapp.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val isbn : Long = -1L,
    val title : String = "",
    val image : String = "",
    val description: Description = Description()
)