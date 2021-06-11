package com.example.gvdmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Country (
    val country: String,
    val code: String,
) : Parcelable
