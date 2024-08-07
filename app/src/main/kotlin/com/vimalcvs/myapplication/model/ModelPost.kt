package com.vimalcvs.myapplication.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) : Parcelable