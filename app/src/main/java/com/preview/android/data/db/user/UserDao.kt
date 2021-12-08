package com.preview.android.data.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.preview.android.data.db.user.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phone: UserEntity)
}