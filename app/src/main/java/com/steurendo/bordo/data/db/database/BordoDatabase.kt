package com.steurendo.bordo.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steurendo.bordo.data.db.dao.AlbumDao
import com.steurendo.bordo.data.db.entities.AlbumEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class BordoDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var Instance: BordoDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): BordoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BordoDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration(true)
//                    .allowMainThreadQueries() // Uncomment this to clear the DB
                    .build()
                    .also { Instance = it }
//                    .apply { this.clearAllTables() } // Uncomment this to clear the DB
            }
        }
    }
}