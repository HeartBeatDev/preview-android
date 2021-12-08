package com.preview.android.data.db.config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.preview.android.data.db.config.entities.ConfigEntity

@Dao
interface ConfigDao {

    @Query("SELECT * FROM configentity")
    suspend fun getConfig(): ConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: ConfigEntity)
}