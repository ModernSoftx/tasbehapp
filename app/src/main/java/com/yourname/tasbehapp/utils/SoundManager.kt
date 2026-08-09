package com.yourname.tasbehapp.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.yourname.tasbehapp.R

/**
 * SoundManager handles audio feedback for the app.
 * Use LogCat tag "TasbehSound" to debug sound issues.
 */
class SoundManager(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {

    private val TAG = "TasbehSound"
    private var soundPool: SoundPool
    private var clickSoundId: Int = 0
    private var targetReachedSoundId: Int = 0
    private var resetSoundId: Int = 0

    private var isEnabled = true
    private var clickVolume = 1.0f
    private var successVolume = 1.0f
    private var resetVolume = 1.0f
    
    // Map to track individual sound load status
    private val loadedSounds = mutableSetOf<Int>()

    init {
        preferenceManager.migrateVolumeSettings()
        
        // Load volumes after potential migration
        isEnabled = preferenceManager.isSoundEnabled
        clickVolume = preferenceManager.clickVolume
        successVolume = preferenceManager.successVolume
        resetVolume = preferenceManager.resetVolume

        Log.d(TAG, "Initializing SoundPool with volumes: click=$clickVolume, success=$successVolume, reset=$resetVolume")
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) {
                Log.d(TAG, "Sound loaded successfully: ID=$sampleId")
                loadedSounds.add(sampleId)
            } else {
                Log.e(TAG, "Failed to load sound: ID=$sampleId, Status=$status")
            }
        }

        loadSounds()
    }

    /**
     * Refreshes the volume levels from PreferenceManager.
     * Call this when returning from settings to ensure volumes are in sync.
     */
    fun refreshVolumesFromPreferences() {
        isEnabled = preferenceManager.isSoundEnabled
        clickVolume = preferenceManager.clickVolume
        successVolume = preferenceManager.successVolume
        resetVolume = preferenceManager.resetVolume
        Log.d(TAG, "Volumes refreshed: click=$clickVolume, success=$successVolume, reset=$resetVolume")
    }

    private fun loadSounds() {
        try {
            Log.d(TAG, "Starting to load sounds from res/raw...")
            
            // Check if resources exist conceptually (SoundPool.load will throw if not found)
            clickSoundId = soundPool.load(context, R.raw.tasbih_click, 1)
            Log.d(TAG, "Requested Click Sound (R.raw.tasbih_click) -> ID: $clickSoundId")
            
            targetReachedSoundId = soundPool.load(context, R.raw.tasbih_success, 1)
            Log.d(TAG, "Requested Success Sound (R.raw.tasbih_success) -> ID: $targetReachedSoundId")
            
            resetSoundId = soundPool.load(context, R.raw.tasbih_reset, 1)
            Log.d(TAG, "Requested Reset Sound (R.raw.tasbih_reset) -> ID: $resetSoundId")

        } catch (e: Exception) {
            Log.e(TAG, "Critical error loading sounds: ${e.message}")
            e.printStackTrace()
        }
    }

    fun playClickSound(): String {
        return play(clickSoundId, "Click", clickVolume)
    }

    fun playTargetReachedSound(): String {
        return play(targetReachedSoundId, "Target Reached", successVolume)
    }

    fun playResetSound(): String {
        return play(resetSoundId, "Reset", resetVolume)
    }

    private fun play(soundId: Int, name: String, volume: Float): String {
        Log.d(TAG, "Attempting to play $name (ID: $soundId)")
        
        if (!isEnabled) {
            val msg = "Sound is disabled in settings."
            Log.w(TAG, msg)
            return msg
        }
        
        if (soundId == 0) {
            val msg = "Error: $name sound ID is 0 (Not initialized)."
            Log.e(TAG, msg)
            return msg
        }
        
        if (!loadedSounds.contains(soundId)) {
            val msg = "Warning: $name sound not loaded yet. status: ${loadedSounds.size} sounds ready."
            Log.w(TAG, msg)
            return msg
        }

        val result = soundPool.play(soundId, volume, volume, 1, 0, 1.0f)
        if (result == 0) {
            val msg = "Failed to play $name. SoundPool returned 0."
            Log.e(TAG, msg)
            return msg
        } else {
            val msg = "Successfully playing $name (Stream ID: $result)"
            Log.d(TAG, msg)
            return msg
        }
    }

    fun setEnabled(enabled: Boolean) {
        Log.d(TAG, "Sound enabled set to: $enabled")
        isEnabled = enabled
    }

    @Deprecated("Use specific volume setters instead", ReplaceWith("setClickVolume(vol), setSuccessVolume(vol), setResetVolume(vol)"))
    fun setVolume(vol: Float) {
        val coercedVol = vol.coerceIn(0f, 1f)
        clickVolume = coercedVol
        successVolume = coercedVol
        resetVolume = coercedVol
        Log.d(TAG, "Global volume set to: $coercedVol")
    }

    fun setClickVolume(vol: Float) {
        clickVolume = vol.coerceIn(0f, 1f)
        Log.d(TAG, "Click volume set to: $clickVolume")
    }

    fun setSuccessVolume(vol: Float) {
        successVolume = vol.coerceIn(0f, 1f)
        Log.d(TAG, "Success volume set to: $successVolume")
    }

    fun setResetVolume(vol: Float) {
        resetVolume = vol.coerceIn(0f, 1f)
        Log.d(TAG, "Reset volume set to: $resetVolume")
    }

    fun isReady(): Boolean {
        val ready = loadedSounds.size >= 3
        Log.d(TAG, "SoundManager ready status: $ready (${loadedSounds.size}/3 sounds loaded)")
        return ready
    }

    fun release() {
        Log.d(TAG, "Releasing SoundPool")
        soundPool.release()
        loadedSounds.clear()
    }
}
