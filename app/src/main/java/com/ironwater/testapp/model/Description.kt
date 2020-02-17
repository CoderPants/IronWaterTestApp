package com.ironwater.testapp.model

import com.google.gson.annotations.SerializedName

data class Description(
    val company : String,
    val url : String,
    @SerializedName("company_logo")
    val companyLogo : String,
    val about : String
)