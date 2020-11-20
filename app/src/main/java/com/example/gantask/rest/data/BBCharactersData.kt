package com.example.gantask.rest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BBCharactersData(
    @SerializedName("char_id") val char_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("birthday") val birthday : String,
    @SerializedName("occupation") val occupation : List<String>,
    @SerializedName("img") val img : String,
    @SerializedName("status") val status : String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("appearance") val appearance : List<Int>,
    @SerializedName("portrayed") val portrayed : String,
    @SerializedName("category") val category : String,
    @SerializedName("better_call_saul_appearance") val better_call_saul_appearance : List<String>
) : Parcelable
