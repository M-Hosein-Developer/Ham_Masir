package ir.androidcoder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.androidcoder.data.local.entities.HomeEntity
import ir.androidcoder.data.local.entities.SearchEntity

@Dao
interface MyDao {

    //Search
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(myEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity")
    suspend fun getSearchItems(): List<SearchEntity>

    //Home
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeItem(myEntity: HomeEntity)

    @Query("SELECT * FROM HomeEntity")
    suspend fun getHomeItems(): HomeEntity



}