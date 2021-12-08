package com.preview.android.data.db.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.preview.android.data.db.auth.entities.ActionEntity

@Dao
interface ActionDao {

    @Query("SELECT * FROM actionentity")
    suspend fun getAction(): ActionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phone: ActionEntity)
}