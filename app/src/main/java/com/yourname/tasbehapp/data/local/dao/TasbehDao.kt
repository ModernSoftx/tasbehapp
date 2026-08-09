package com.yourname.tasbehapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yourname.tasbehapp.data.local.entity.TasbehEntity

@Dao
interface TasbehDao {
    
    @Query("SELECT * FROM tasbeh_table ORDER BY isFavourite DESC, lastUsed DESC")
    fun getAllTasbehs(): LiveData<List<TasbehEntity>>
    
    @Query("SELECT * FROM tasbeh_table WHERE id = :id")
    suspend fun getTasbehById(id: Long): TasbehEntity?
    
    @Query("SELECT * FROM tasbeh_table WHERE isActive = 1 LIMIT 1")
    fun getActiveTasbeh(): LiveData<TasbehEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasbeh(tasbeh: TasbehEntity): Long
    
    @Update
    suspend fun updateTasbeh(tasbeh: TasbehEntity)
    
    @Delete
    suspend fun deleteTasbeh(tasbeh: TasbehEntity)
    
    @Query("UPDATE tasbeh_table SET currentCount = :count, lastUsed = :timestamp WHERE id = :id")
    suspend fun updateCount(id: Long, count: Int, timestamp: Long)
    
    @Query("UPDATE tasbeh_table SET isActive = 0")
    suspend fun deactivateAll()

    @Query("UPDATE tasbeh_table SET isActive = 1 WHERE id = :id")
    suspend fun setActive(id: Long)
    
    @Query("UPDATE tasbeh_table SET currentCount = 0 WHERE id = :id")
    suspend fun resetCount(id: Long)
    
    @Query("SELECT * FROM tasbeh_table WHERE name LIKE '%' || :query || '%'")
    fun searchTasbehs(query: String): LiveData<List<TasbehEntity>>

    @Query("SELECT COUNT(*) FROM tasbeh_table")
    suspend fun getTasbehCount(): Int

    @Query("""
        SELECT * FROM tasbeh_table 
        WHERE id != :excludeId 
        ORDER BY isFavourite DESC, lastUsed DESC 
        LIMIT 1
    """)
    suspend fun getNextBestTasbeh(excludeId: Long): TasbehEntity?

    @Query("""
        SELECT * FROM tasbeh_table 
        ORDER BY isFavourite DESC, lastUsed DESC 
        LIMIT 1
    """)
    suspend fun getLatestTasbeh(): TasbehEntity?

    @Query("UPDATE tasbeh_table SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun toggleFavourite(id: Long, isFavourite: Boolean)

    @Query("SELECT * FROM tasbeh_table WHERE isFavourite = 1 ORDER BY lastUsed DESC")
    fun getFavouriteTasbehs(): LiveData<List<TasbehEntity>>

    @Query("UPDATE tasbeh_table SET currentCount = :count, totalLaps = :laps, lastUsed = :timestamp WHERE id = :id")
    suspend fun updateCountAndLaps(id: Long, count: Int, laps: Int, timestamp: Long)

    @Query("SELECT * FROM tasbeh_table WHERE isActive = 1")
    suspend fun getActiveTasbehsSync(): List<TasbehEntity>
}
