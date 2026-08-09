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
        
        // New per-sound volume keys
        private const val KEY_CLICK_VOLUME = "click_volume"
        private const val KEY_SUCCESS_VOLUME = "success_volume"
        private const val KEY_RESET_VOLUME = "reset_volume"
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
    
    var hapticIntensity: Int
        get() = prefs.getInt(KEY_HAPTIC_INTENSITY, 40)
        set(value) = prefs.edit().putInt(KEY_HAPTIC_INTENSITY, value).apply()
    
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

    var clickVolume: Float
        get() = prefs.getFloat(KEY_CLICK_VOLUME, 1.0f)
        set(value) = prefs.edit().putFloat(KEY_CLICK_VOLUME, value).apply()

    var successVolume: Float
        get() = prefs.getFloat(KEY_SUCCESS_VOLUME, 1.0f)
        set(value) = prefs.edit().putFloat(KEY_SUCCESS_VOLUME, value).apply()

    var resetVolume: Float
        get() = prefs.getFloat(KEY_RESET_VOLUME, 1.0f)
        set(value) = prefs.edit().putFloat(KEY_RESET_VOLUME, value).apply()

    fun migrateVolumeSettings() {
        if (!prefs.contains(KEY_CLICK_VOLUME)) {
            val oldVolume = soundVolume
            clickVolume = oldVolume
            successVolume = oldVolume
            resetVolume = oldVolume
        }
    }
}
