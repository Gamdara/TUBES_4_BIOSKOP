package com.kel4.tubes_4_bioskop.entity

import com.google.gson.annotations.SerializedName

class ResponseCreate (
    @SerializedName("status") val stt:Int,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val pesan:String,
)