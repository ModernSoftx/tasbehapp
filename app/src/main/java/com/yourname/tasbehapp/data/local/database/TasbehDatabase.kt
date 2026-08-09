package com.yourname.tasbehapp.data.local.database  // ← ADD THIS AS LINE 1

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yourname.tasbehapp.data.local.dao.TasbehDao
import com.yourname.tasbehapp.data.local.entity.TasbehEntity

@Database(entities = [TasbehEntity::class], version = 3, exportSchema = true)
abstract class TasbehDatabase : RoomDatabase() {
    abstract fun tasbehDao(): TasbehDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasbeh_table ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasbeh_table ADD COLUMN totalLaps INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
