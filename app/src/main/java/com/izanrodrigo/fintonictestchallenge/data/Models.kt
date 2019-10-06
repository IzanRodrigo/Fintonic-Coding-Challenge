package com.izanrodrigo.fintonictestchallenge.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Izan on 2019-10-02.
 */

@Parcelize
data class Superhero(
    val name: String,
    val realName: String,
    val photo: String
) : Parcelable

@Entity(tableName = AppDatabase.TABLE_NAME_SUPERHEROES)
@Parcelize
data class SuperheroDetail(
    @PrimaryKey val name: String,
    @ColumnInfo val realName: String,
    @ColumnInfo val photo: String,
    @ColumnInfo val height: String,
    @ColumnInfo val power: String,
    @ColumnInfo val abilities: String,
    @ColumnInfo val groups: String
) : Parcelable