package ir.androidcoder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.androidcoder.data.local.entities.SearchEntity

@Dao
interface MyDao {

    //Search
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(myEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity")
    suspend fun getSearchItems(): List<SearchEntity>

}