package ir.androidcoder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.androidcoder.data.local.entities.HomeEntity
import ir.androidcoder.data.local.entities.SearchEntity
import ir.androidcoder.data.local.entities.WorkEntity

@Database(entities = [SearchEntity::class , HomeEntity::class , WorkEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase(){

    abstract fun myDao(): MyDao

}