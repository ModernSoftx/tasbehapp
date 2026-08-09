package com.yourname.tasbehapp.di

import com.yourname.tasbehapp.data.local.database.TasbehDatabase
import android.content.Context
import androidx.room.Room
import com.yourname.tasbehapp.data.local.dao.TasbehDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideTasbehDatabase(
        @ApplicationContext context: Context
    ): TasbehDatabase {
        return Room.databaseBuilder(
            context,
            TasbehDatabase::class.java,
            "tasbeh_database"
        )
            .addMigrations(TasbehDatabase.MIGRATION_1_2, TasbehDatabase.MIGRATION_2_3)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideTasbehDao(database: TasbehDatabase): TasbehDao {
        return database.tasbehDao()
    }
}
