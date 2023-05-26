package com.ismael.petsinnocv.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "user")
data class User (
  @ColumnInfo(name = "id")
  @PrimaryKey(autoGenerate = true)
  val id : Int = 0,
  val name : String? = "",
  val birthdate : String? = null
  ) {
}