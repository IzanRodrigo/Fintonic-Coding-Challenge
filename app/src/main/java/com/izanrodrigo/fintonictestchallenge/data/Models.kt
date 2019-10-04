package com.izanrodrigo.fintonictestchallenge.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Izan on 2019-10-02.
 */

@Parcelize
data class Superhero(
    val name: String,
    val photo: String,
    val realName: String,
    val height: String,
    val power: String,
    val abilities: String,
    val groups: String
) : Parcelable