package com.yourname.tasbehapp.ui.settings

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.utils.SoundManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SoundTestActivity : AppCompatActivity() {

    @Inject
    lateinit var soundManager: SoundManager

    private lateinit var tvDebugLogs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_test)

        tvDebugLogs = findViewById(R.id.tvDebugLogs)
        val btnTestClick = findViewById<Button>(R.id.btnTestClick)
        val btnTestSuccess = findViewById<Button>(R.id.btnTestSuccess)
        val btnTestReset = findViewById<Button>(R.id.btnTestReset)
        val btnClearLogs = findViewById<Button>(R.id.btnClearLogs)

        logToScreen("Activity started. Initializing SoundManager...")
        
        // Ensure sound is enabled for test
        soundManager.setEnabled(true)
        soundManager.setVolume(1.0f)

        btnTestClick.setOnClickListener {
            val result = soundManager.playClickSound()
            logToScreen("Click: $result")
        }

        btnTestSuccess.setOnClickListener {
            val result = soundManager.playTargetReachedSound()
            logToScreen("Success: $result")
        }

        btnTestReset.setOnClickListener {
            val result = soundManager.playResetSound()
            logToScreen("Reset: $result")
        }

        btnClearLogs.setOnClickListener {
            tvDebugLogs.text = "Logs cleared.\n"
        }
        
        // Initial check
        if (soundManager.isReady()) {
            logToScreen("SoundManager is READY.")
        } else {
            logToScreen("SoundManager is still loading sounds...")
        }
    }

    private fun logToScreen(message: String) {
        val currentTime = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
        val logLine = "[$currentTime] $message\n"
        tvDebugLogs.append(logLine)
    }
}
