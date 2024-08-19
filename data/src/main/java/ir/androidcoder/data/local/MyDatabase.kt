package ir.androidcoder.data.local

import androidx.room.Database
import ir.androidcoder.data.local.entities.SearchEntity

@Database(entities = [SearchEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase {

    abstract fun myDao(): MyDao

}