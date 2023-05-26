package com.ismael.petsinnocv.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ismael.petsinnocv.data.model.User

@Dao
interface UserDao {
  @Query("SELECT * FROM USER")
  suspend fun getAllUsers() : List<User>


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(users : List<User>)


  @Query("DELETE FROM USER")
  suspend fun deleteAllUsers()

}