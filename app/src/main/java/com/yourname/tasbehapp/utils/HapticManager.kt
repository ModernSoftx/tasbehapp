package com.yourname.tasbehapp.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class HapticManager(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {
    
    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    
    private var isEnabled = true
    private var intensityMs: Int = 40

    init {
        isEnabled = preferenceManager.isHapticEnabled
        intensityMs = preferenceManager.hapticIntensity
    }
    
    fun performClickFeedback() {
        if (!isEnabled) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val amplitude = if (intensityMs >= 70) 255 else 255 // Always use max amplitude for better feeling
            val effect = VibrationEffect.createOneShot(intensityMs.toLong(), amplitude)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(intensityMs.toLong())
        }
    }
    
    fun performDecrementFeedback() {
        if (!isEnabled) return
        val duration = (intensityMs * 0.7).toLong().coerceAtLeast(15)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, 255))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
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
    
    /**
     * Updates haptic intensity AND provides immediate user feedback.
     * Use this ONLY in SettingsActivity for user-initiated changes.
     */
    fun setIntensityWithPreview(durationMs: Int) {
        intensityMs = durationMs
        if (!isEnabled) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(durationMs.toLong(), VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(durationMs.toLong())
        }
    }

    /**
     * Updates haptic intensity silently without triggering vibration.
     * Use this for initialization and resume lifecycle events.
     */
    fun updateIntensity(durationMs: Int) {
        intensityMs = durationMs
        android.util.Log.d("HapticManager", "Intensity updated silently to ${durationMs}ms")
    }

    /**
     * Refreshes all haptic settings from PreferenceManager without triggering feedback.
     * Use this in onResume() or initialization.
     */
    fun refreshFromPreferences() {
        isEnabled = preferenceManager.isHapticEnabled
        updateIntensity(preferenceManager.hapticIntensity)
        android.util.Log.d("HapticManager", "Settings refreshed: enabled=$isEnabled, intensity=${intensityMs}ms")
    }
}
