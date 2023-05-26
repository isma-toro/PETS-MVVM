package com.ismael.petsinnocv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ismael.petsinnocv.core.utils.RoomTypeConverters
import com.ismael.petsinnocv.data.database.dao.UserDao
import com.ismael.petsinnocv.data.model.User

@Database(
  entities = [
    User::class
  ],
  version = 1,
  exportSchema = false
)

@TypeConverters(RoomTypeConverters::class)
abstract class PetsInnocvDatabase : RoomDatabase(){
  abstract fun userDao() : UserDao
}