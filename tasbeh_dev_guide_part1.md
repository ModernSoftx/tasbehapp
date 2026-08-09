# Tasbeh App - Step-by-Step Development Guide

## 📋 Project Setup

### Step 1: Create New Android Project
```
File → New → New Project
Select: Empty Activity
Name: TasbehApp
Package name: com.yourname.tasbehapp
Language: Kotlin
Minimum SDK: API 24 (Android 7.0)
Build configuration language: Kotlin DSL
```

### Step 2: Configure build.gradle.kts (Module: app)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yourname.tasbehapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yourname.tasbehapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
```

### Step 3: Configure build.gradle.kts (Project level)

```kotlin
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
```

---

## 🏗️ Project Structure

### Step 4: Create Package Structure

```
app/src/main/java/com/yourname/tasbehapp/
├── di/                          # Dependency Injection
│   ├── AppModule.kt
│   └── DatabaseModule.kt
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   └── TasbehEntity.kt
│   │   ├── dao/
│   │   │   └── TasbehDao.kt
│   │   └── database/
│   │       └── TasbehDatabase.kt
│   └── repository/
│       └── TasbehRepository.kt
├── domain/
│   └── model/
│       └── Tasbeh.kt
├── ui/
│   ├── counter/
│   │   ├── CounterActivity.kt
│   │   ├── CounterViewModel.kt
│   │   └── CounterViewState.kt
│   ├── list/
│   │   ├── TasbehListActivity.kt
│   │   ├── TasbehListViewModel.kt
│   │   ├── TasbehAdapter.kt
│   │   └── TasbehViewHolder.kt
│   ├── addEdit/
│   │   ├── AddEditTasbehActivity.kt
│   │   └── AddEditViewModel.kt
│   └── settings/
│       ├── SettingsActivity.kt
│       └── SettingsViewModel.kt
├── utils/
│   ├── SoundManager.kt
│   ├── HapticManager.kt
│   ├── PreferenceManager.kt
│   └── Constants.kt
└── TasbehApplication.kt
```

---

## 💾 Database Layer

### Step 5: Create Domain Model (Tasbeh.kt)

```kotlin
package com.yourname.tasbehapp.domain.model

data class Tasbeh(
    val id: Long = 0,
    val name: String,
    val arabicText: String? = null,
    val currentCount: Int = 0,
    val targetCount: Int = 33,
    val category: String = "Custom",
    val description: String? = null,
    val lastUsed: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)
```

### Step 6: Create Database Entity (TasbehEntity.kt)

```kotlin
package com.yourname.tasbehapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourname.tasbehapp.domain.model.Tasbeh

@Entity(tableName = "tasbeh_table")
data class TasbehEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val arabicText: String? = null,
    val currentCount: Int = 0,
    val targetCount: Int = 33,
    val category: String = "Custom",
    val description: String? = null,
    val lastUsed: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)

// Extension functions for conversion
fun TasbehEntity.toDomain(): Tasbeh {
    return Tasbeh(
        id = id,
        name = name,
        arabicText = arabicText,
        currentCount = currentCount,
        targetCount = targetCount,
        category = category,
        description = description,
        lastUsed = lastUsed,
        createdAt = createdAt,
        isActive = isActive
    )
}

fun Tasbeh.toEntity(): TasbehEntity {
    return TasbehEntity(
        id = id,
        name = name,
        arabicText = arabicText,
        currentCount = currentCount,
        targetCount = targetCount,
        category = category,
        description = description,
        lastUsed = lastUsed,
        createdAt = createdAt,
        isActive = isActive
    )
}
```

### Step 7: Create DAO (TasbehDao.kt)

```kotlin
package com.yourname.tasbehapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yourname.tasbehapp.data.local.entity.TasbehEntity

@Dao
interface TasbehDao {
    
    @Query("SELECT * FROM tasbeh_table ORDER BY lastUsed DESC")
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
}
```

### Step 8: Create Database (TasbehDatabase.kt)

```kotlin
package com.yourname.tasbehapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourname.tasbehapp.data.local.dao.TasbehDao
import com.yourname.tasbehapp.data.local.entity.TasbehEntity

@Database(
    entities = [TasbehEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TasbehDatabase : RoomDatabase() {
    abstract fun tasbehDao(): TasbehDao
}
```

### Step 9: Create Repository (TasbehRepository.kt)

```kotlin
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
    
    suspend fun getTasbehById(id: Long): Tasbeh? {
        return tasbehDao.getTasbehById(id)?.toDomain()
    }
    
    suspend fun insertTasbeh(tasbeh: Tasbeh): Long {
        return tasbehDao.insertTasbeh(tasbeh.toEntity())
    }
    
    suspend fun updateTasbeh(tasbeh: Tasbeh) {
        tasbehDao.updateTasbeh(tasbeh.toEntity())
    }
    
    suspend fun deleteTasbeh(tasbeh: Tasbeh) {
        tasbehDao.deleteTasbeh(tasbeh.toEntity())
    }
    
    suspend fun incrementCount(tasbeh: Tasbeh) {
        val newCount = tasbeh.currentCount + 1
        tasbehDao.updateCount(
            id = tasbeh.id,
            count = newCount,
            timestamp = System.currentTimeMillis()
        )
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
}
```

---

## 🔧 Dependency Injection (Hilt)

### Step 10: Create Application Class (TasbehApplication.kt)

```kotlin
package com.yourname.tasbehapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TasbehApplication : Application()
```

Update AndroidManifest.xml:
```xml
<application
    android:name=".TasbehApplication"
    ...>
```

### Step 11: Create Hilt Modules (DatabaseModule.kt)

```kotlin
package com.yourname.tasbehapp.di

import android.content.Context
import androidx.room.Room
import com.yourname.tasbehapp.data.local.dao.TasbehDao
import com.yourname.tasbehapp.data.local.database.TasbehDatabase
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
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun provideTasbehDao(database: TasbehDatabase): TasbehDao {
        return database.tasbehDao()
    }
}
```

### Step 12: Create AppModule (AppModule.kt)

```kotlin
package com.yourname.tasbehapp.di

import android.content.Context
import com.yourname.tasbehapp.utils.HapticManager
import com.yourname.tasbehapp.utils.PreferenceManager
import com.yourname.tasbehapp.utils.SoundManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideSoundManager(
        @ApplicationContext context: Context
    ): SoundManager {
        return SoundManager(context)
    }
    
    @Provides
    @Singleton
    fun provideHapticManager(
        @ApplicationContext context: Context
    ): HapticManager {
        return HapticManager(context)
    }
    
    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ): PreferenceManager {
        return PreferenceManager(context)
    }
}
```

---

## 🎵 Utility Classes

### Step 13: Create SoundManager (SoundManager.kt)

```kotlin
package com.yourname.tasbehapp.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.yourname.tasbehapp.R

class SoundManager(private val context: Context) {
    
    private var soundPool: SoundPool
    private var clickSoundId: Int = 0
    private var targetReachedSoundId: Int = 0
    private var resetSoundId: Int = 0
    
    private var isEnabled = true
    private var volume = 1.0f
    
    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()
        
        loadSounds()
    }
    
    private fun loadSounds() {
        // Load your sound files from res/raw folder
        // clickSoundId = soundPool.load(context, R.raw.click_sound, 1)
        // targetReachedSoundId = soundPool.load(context, R.raw.success_sound, 1)
        // resetSoundId = soundPool.load(context, R.raw.reset_sound, 1)
    }
    
    fun playClickSound() {
        if (isEnabled && clickSoundId != 0) {
            soundPool.play(clickSoundId, volume, volume, 1, 0, 1.0f)
        }
    }
    
    fun playTargetReachedSound() {
        if (isEnabled && targetReachedSoundId != 0) {
            soundPool.play(targetReachedSoundId, volume, volume, 1, 0, 1.0f)
        }
    }
    
    fun playResetSound() {
        if (isEnabled && resetSoundId != 0) {
            soundPool.play(resetSoundId, volume, volume, 1, 0, 1.0f)
        }
    }
    
    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }
    
    fun setVolume(vol: Float) {
        volume = vol.coerceIn(0f, 1f)
    }
    
    fun release() {
        soundPool.release()
    }
}
```

### Step 14: Create HapticManager (HapticManager.kt)

```kotlin
package com.yourname.tasbehapp.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class HapticManager(private val context: Context) {
    
    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    
    private var isEnabled = true
    private var intensity = HapticIntensity.MEDIUM
    
    enum class HapticIntensity(val duration: Long) {
        LIGHT(10),
        MEDIUM(20),
        STRONG(30)
    }
    
    fun performClickFeedback() {
        if (!isEnabled) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(
                intensity.duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(intensity.duration)
        }
    }
    
    fun performTargetReachedFeedback() {
        if (!isEnabled) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 50, 50, 50)
            val amplitudes = intArrayOf(0, 255, 0, 255)
            val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 50, 50, 50), -1)
        }
    }
    
    fun performLongPressFeedback() {
        if (!isEnabled) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(100)
        }
    }
    
    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }
    
    fun setIntensity(newIntensity: HapticIntensity) {
        intensity = newIntensity
    }
}
```

### Step 15: Create PreferenceManager (PreferenceManager.kt)

```kotlin
package com.yourname.tasbehapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "tasbeh_preferences"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_HAPTIC_ENABLED = "haptic_enabled"
        private const val KEY_SOUND_VOLUME = "sound_volume"
        private const val KEY_HAPTIC_INTENSITY = "haptic_intensity"
        private const val KEY_THEME = "theme"
        private const val KEY_KEEP_SCREEN_ON = "keep_screen_on"
        private const val KEY_AUTO_RESET = "auto_reset"
        private const val KEY_SHOW_CELEBRATION = "show_celebration"
    }
    
    var isSoundEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND_ENABLED, value).apply()
    
    var isHapticEnabled: Boolean
        get() = prefs.getBoolean(KEY_HAPTIC_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_HAPTIC_ENABLED, value).apply()
    
    var soundVolume: Float
        get() = prefs.getFloat(KEY_SOUND_VOLUME, 1.0f)
        set(value) = prefs.edit().putFloat(KEY_SOUND_VOLUME, value).apply()
    
    var hapticIntensity: String
        get() = prefs.getString(KEY_HAPTIC_INTENSITY, "MEDIUM") ?: "MEDIUM"
        set(value) = prefs.edit().putString(KEY_HAPTIC_INTENSITY, value).apply()
    
    var theme: String
        get() = prefs.getString(KEY_THEME, "AUTO") ?: "AUTO"
        set(value) = prefs.edit().putString(KEY_THEME, value).apply()
    
    var keepScreenOn: Boolean
        get() = prefs.getBoolean(KEY_KEEP_SCREEN_ON, false)
        set(value) = prefs.edit().putBoolean(KEY_KEEP_SCREEN_ON, value).apply()
    
    var autoReset: Boolean
        get() = prefs.getBoolean(KEY_AUTO_RESET, false)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_RESET, value).apply()
    
    var showCelebration: Boolean
        get() = prefs.getBoolean(KEY_SHOW_CELEBRATION, true)
        set(value) = prefs.edit().putBoolean(KEY_SHOW_CELEBRATION, value).apply()
}
```

### Step 16: Create Constants (Constants.kt)

```kotlin
package com.yourname.tasbehapp.utils

object Constants {
    
    // Default Tasbehs
    val DEFAULT_TASBEHS = listOf(
        DefaultTasbeh(
            name = "SubhanAllah",
            arabicText = "سُبْحَانَ ٱللَّٰهِ",
            targetCount = 33,
            category = "Dhikr"
        ),
        DefaultTasbeh(
            name = "Alhamdulillah",
            arabicText = "ٱلْحَمْدُ لِلَّٰهِ",
            targetCount = 33,
            category = "Dhikr"
        ),
        DefaultTasbeh(
            name = "Allahu Akbar",
            arabicText = "ٱللَّٰهُ أَكْبَرُ",
            targetCount = 34,
            category = "Dhikr"
        ),
        DefaultTasbeh(
            name = "Astaghfirullah",
            arabicText = "أَسْتَغْفِرُ ٱللَّٰهَ",
            targetCount = 100,
            category = "Dhikr"
        ),
        DefaultTasbeh(
            name = "La ilaha illallah",
            arabicText = "لَا إِلَٰهَ إِلَّا ٱللَّٰهُ",
            targetCount = 100,
            category = "Dhikr"
        )
    )
    
    data class DefaultTasbeh(
        val name: String,
        val arabicText: String,
        val targetCount: Int,
        val category: String
    )
    
    // Animation durations
    const val ANIM_DURATION_SHORT = 150L
    const val ANIM_DURATION_MEDIUM = 300L
    const val ANIM_DURATION_LONG = 500L
    
    // Target count presets
    val TARGET_COUNT_PRESETS = listOf(11, 33, 99, 100, 500, 1000)
    
    // Intent extras
    const val EXTRA_TASBEH_ID = "extra_tasbeh_id"
    const val EXTRA_IS_EDIT_MODE = "extra_is_edit_mode"
}
```

---

## 🎨 UI Layer - Counter Screen

### Step 17: Create CounterViewState (CounterViewState.kt)

```kotlin
package com.yourname.tasbehapp.ui.counter

import com.yourname.tasbehapp.domain.model.Tasbeh

sealed class CounterViewState {
    object Loading : CounterViewState()
    data class Success(
        val tasbeh: Tasbeh,
        val progress: Float
    ) : CounterViewState()
    object NoTasbeh : CounterViewState()
    data class Error(val message: String) : CounterViewState()
}

sealed class CounterEvent {
    object TargetReached : CounterEvent()
    object CountIncremented : CounterEvent()
    object CountReset : CounterEvent()
}
```

### Step 18: Create CounterViewModel (CounterViewModel.kt)

```kotlin
package com.yourname.tasbehapp.ui.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.tasbehapp.data.repository.TasbehRepository
import com.yourname.tasbehapp.domain.model.Tasbeh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val repository: TasbehRepository
) : ViewModel() {
    
    private val _viewState = MutableLiveData<CounterViewState>()
    val viewState: LiveData<CounterViewState> = _viewState
    
    private val _event = MutableLiveData<CounterEvent>()
    val event: LiveData<CounterEvent> = _event
    
    private var currentTasbeh: Tasbeh? = null
    
    init {
        loadActiveTasbeh()
    }
    
    private fun loadActiveTasbeh() {
        viewModelScope.launch {
            repository.activeTasbeh.observeForever { tasbeh ->
                if (tasbeh != null) {
                    currentTasbeh = tasbeh
                    updateViewState(tasbeh)
                } else {
                    _viewState.value = CounterViewState.NoTasbeh
                }
            }
        }
    }
    
    fun incrementCount() {
        viewModelScope.launch {
            currentTasbeh?.let { tasbeh ->
                repository.incrementCount(tasbeh)
                
                val newCount = tasbeh.currentCount + 1
                _event.value = CounterEvent.CountIncremented
                
                if (newCount >= tasbeh.targetCount) {
                    _event.value = CounterEvent.TargetReached
                }
            }
        }
    }
    
    fun resetCount() {
        viewModelScope.launch {
            currentTasbeh?.let { tasbeh ->
                repository.resetCount(tasbeh.id)
                _event.value = CounterEvent.CountReset
            }
        }
    }
    
    fun setActiveTasbeh(tasbehId: Long) {
        viewModelScope.launch {
            repository.setActiveTasbeh(tasbehId)
        }
    }
    
    private fun updateViewState(tasbeh: Tasbeh) {
        val progress = if (tasbeh.targetCount > 0) {
            tasbeh.currentCount.toFloat() / tasbeh.targetCount.toFloat()
        } else {
            0f
        }
        
        _viewState.value = CounterViewState.Success(
            tasbeh = tasbeh,
            progress = progress.coerceIn(0f, 1f)
        )
    }
}
```

---

## 📱 Continue to Part 2 for XML Layouts and Activities

This guide is comprehensive. Would you like me to continue with:
- Part 2: XML Layouts for all screens
- Part 3: Activity implementations
- Part 4: Animations and custom views
- Part 5: RecyclerView adapters
- Part 6: Additional features and polish

Let me know which parts you'd like me to detail next!
