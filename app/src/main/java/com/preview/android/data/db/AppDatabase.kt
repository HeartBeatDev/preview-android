package com.preview.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.preview.android.data.db.auth.ActionDao
import com.preview.android.data.db.auth.entities.ActionEntity
import com.preview.android.data.db.config.ConfigColorsConverter
import com.preview.android.data.db.config.ConfigDao
import com.preview.android.data.db.config.entities.ConfigEntity
import com.preview.android.data.db.user.UserDao
import com.preview.android.data.db.user.entities.UserEntity

@Database(entities = [ConfigEntity::class, UserEntity::class, ActionEntity::class], version = 1)
@TypeConverters(ConfigColorsConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao
    abstract fun userDao(): UserDao
    abstract fun actionDao(): ActionDao
}