package com.yourname.tasbehapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.yourname.tasbehapp.data.local.dao.TasbehDao
import com.yourname.tasbehapp.data.local.entity.toDomain
import com.yourname.tasbehapp.data.local.entity.toEntity
import com.yourname.tasbehapp.domain.model.Tasbeh
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasbehRepository @Inject constructor(
    private val tasbehDao: TasbehDao
) {
    
    val allTasbehs: LiveData<List<Tasbeh>> = 
        tasbehDao.getAllTasbehs().map { entities ->
            entities.map { it.toDomain() }
        }
    
    val activeTasbeh: LiveData<Tasbeh?> = 
        tasbehDao.getActiveTasbeh().map { it?.toDomain() }

    val favouriteTasbehs: LiveData<List<Tasbeh>> = 
        tasbehDao.getFavouriteTasbehs().map { entities ->
            entities.map { it.toDomain() }
        }
    
    suspend fun getTasbehById(id: Long): Tasbeh? {
        return tasbehDao.getTasbehById(id)?.toDomain()
    }
    
    suspend fun insertTasbeh(tasbeh: Tasbeh): Long {
        val count = tasbehDao.getTasbehCount()
        val entity = tasbeh.toEntity()
        
        if (entity.isActive || count == 0) {
            tasbehDao.deactivateAll()
        }
        
        val finalEntity = if (count == 0) {
            entity.copy(isActive = true)
        } else {
            entity
        }
        return tasbehDao.insertTasbeh(finalEntity)
    }
    
    suspend fun updateTasbeh(tasbeh: Tasbeh) {
        tasbehDao.updateTasbeh(tasbeh.toEntity())
    }
    
    suspend fun deleteTasbeh(tasbeh: Tasbeh) {
        val entity = tasbeh.toEntity()
        val wasActive = entity.isActive
        
        tasbehDao.deleteTasbeh(entity)
        
        if (wasActive) {
            handleActiveReassignment(entity.id)
        }
    }
    
    private suspend fun handleActiveReassignment(deletedId: Long) {
        val remainingCount = tasbehDao.getTasbehCount()
        
        if (remainingCount == 0) return
        
        val nextBest = tasbehDao.getNextBestTasbeh(deletedId)
        
        if (nextBest != null) {
            tasbehDao.deactivateAll()
            tasbehDao.setActive(nextBest.id)
            tasbehDao.updateCount(nextBest.id, nextBest.currentCount, System.currentTimeMillis())
        }
    }
    
    suspend fun incrementCount(tasbeh: Tasbeh) {
        val newCount = tasbeh.currentCount + 1
        tasbehDao.updateCount(
            id = tasbeh.id,
            count = newCount,
            timestamp = System.currentTimeMillis()
        )
    }

    suspend fun decrementCount(tasbeh: Tasbeh) {
        if (tasbeh.currentCount > 0) {
            val newCount = tasbeh.currentCount - 1
            tasbehDao.updateCountAndLaps(
                id = tasbeh.id,
                count = newCount,
                laps = tasbeh.totalLaps,
                timestamp = System.currentTimeMillis()
            )
        }
    }

    suspend fun updateCountAndLaps(tasbehId: Long, count: Int, laps: Int) {
        tasbehDao.updateCountAndLaps(
            id = tasbehId,
            count = count,
            laps = laps,
            timestamp = System.currentTimeMillis()
        )
    }

    suspend fun toggleFavourite(tasbehId: Long, isFavourite: Boolean) {
        tasbehDao.toggleFavourite(tasbehId, isFavourite)
    }
    
    suspend fun resetCount(tasbehId: Long) {
        tasbehDao.resetCount(tasbehId)
    }
    
    suspend fun setActiveTasbeh(tasbehId: Long) {
        tasbehDao.deactivateAll()
        tasbehDao.setActive(tasbehId)
    }
    
    fun searchTasbehs(query: String): LiveData<List<Tasbeh>> {
        return tasbehDao.searchTasbehs(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    // Removed checkAndPopulateDefaults as per refactor
}
