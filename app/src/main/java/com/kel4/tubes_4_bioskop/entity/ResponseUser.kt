package com.kel4.tubes_4_bioskop.entity

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("status") val stt:String,
    val totaldata: Int,
    val data:User)