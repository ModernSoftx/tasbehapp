package com.yourname.tasbehapp.ui.counter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yourname.tasbehapp.databinding.ActivityCounterBinding
import com.yourname.tasbehapp.ui.list.TasbehListActivity
import com.yourname.tasbehapp.utils.HapticManager
import com.yourname.tasbehapp.utils.PreferenceManager
import com.yourname.tasbehapp.utils.SoundManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.ui.addEdit.AddEditTasbehActivity

@AndroidEntryPoint
class CounterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCounterBinding
    private val viewModel: CounterViewModel by viewModels()
    
    @Inject
    lateinit var soundManager: SoundManager
    
    @Inject
    lateinit var hapticManager: HapticManager
    
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            // Apply padding to the root or specific header if needed
            // The Counter screen header should have extra padding at the top
            binding.root.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        updateKeepScreenOnSetting()
        setupClickListeners()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        updateKeepScreenOnSetting()
        hapticManager.refreshFromPreferences()
        soundManager.refreshVolumesFromPreferences()
        validateScreenOnFlag()
    }

    private fun setupEdgeToEdge() {
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        androidx.core.view.WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    /**
     * Updates the Keep Screen On setting by applying/removing window flags.
     * Should be called whenever the setting changes (onCreate, onResume).
     */
    private fun updateKeepScreenOnSetting() {
        val isKeepScreenOn = preferenceManager.keepScreenOn
        
        if (isKeepScreenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        
        // Redundant layer for better device compatibility
        binding.root.keepScreenOn = isKeepScreenOn
        
        android.util.Log.d("CounterActivity", "Keep screen on: $isKeepScreenOn")
    }

    /**
     * Validates that the Keep Screen On flag is actually applied.
     * Useful for debugging device-specific issues.
     */
    private fun validateScreenOnFlag() {
        val hasFlag = (window.attributes.flags and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0
        val preferenceEnabled = preferenceManager.keepScreenOn
        
        if (preferenceEnabled != hasFlag) {
            android.util.Log.w("CounterActivity", 
                "Screen flag mismatch! Preference: $preferenceEnabled, Actual flag: $hasFlag")
        } else {
            android.util.Log.d("CounterActivity", "Screen flag verified: $hasFlag")
        }
    }

    private fun setupClickListeners() {
        binding.rippleOverlay.setOnClickListener {
            viewModel.incrementCount()
        }

        binding.rippleOverlay.setOnLongClickListener {
            hapticManager.performLongPressFeedback()
            showResetDialog()
            true
        }

        binding.ivSettings.setOnClickListener {
            startActivity(android.content.Intent(this, com.yourname.tasbehapp.ui.settings.SettingsActivity::class.java))
        }

        binding.btnReset.setOnClickListener {
            showResetDialog()
        }

        binding.btnChangeTasbeh.setOnClickListener {
            startActivity(android.content.Intent(this, TasbehListActivity::class.java))
        }

        binding.btnDecrement.setOnClickListener {
            viewModel.decrementCount()
        }
        
        setupEmptyStateActions()
    }

    private fun setupEmptyStateActions() {
        binding.btnCreateFirstTasbeh.setOnClickListener {
            val intent = android.content.Intent(this, AddEditTasbehActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                is CounterViewState.Success -> {
                    showCounterUI()
                    val tasbeh = state.tasbeh
                    binding.tvTasbehTitle.text = tasbeh.name
                    binding.tvArabicText.text = tasbeh.arabicText
                    binding.tvCurrentCount.text = tasbeh.currentCount.toString()
                    binding.tvTargetCount.text = getString(R.string.target_format, tasbeh.targetCount)
                    binding.tvLapsCount.text = getString(R.string.laps_format, tasbeh.totalLaps)
                    binding.progressCircle.setProgress(state.progress)
                }
                is CounterViewState.NoTasbeh -> {
                    showEmptyState()
                }
                else -> {}
            }
        }

        viewModel.event.observe(this) { event ->
            event.getContentIfNotHandled()?.let { counterEvent ->
                when (counterEvent) {
                    is CounterEvent.CountIncremented -> {
                        soundManager.playClickSound()
                        hapticManager.performClickFeedback()
                        animateCounterIncrement()
                        animateCounterPress()
                    }
                    is CounterEvent.CountDecremented -> {
                        hapticManager.performClickFeedback()
                        animateCounterDecrement()
                    }
                    is CounterEvent.TargetReached -> {
                        soundManager.playTargetReachedSound()
                        hapticManager.performTargetReachedFeedback()
                        animateCounterIncrement()
                        animateCounterPress()
                    }
                    is CounterEvent.LapCompleted -> {
                        soundManager.playTargetReachedSound()
                        hapticManager.performTargetReachedFeedback()
                        animateCounterIncrement()
                        animateCounterPress()
                        android.widget.Toast.makeText(this, getString(R.string.lap_completed_toast, counterEvent.lapNumber), android.widget.Toast.LENGTH_SHORT).show()
                    }
                    is CounterEvent.CountReset -> {
                        soundManager.playResetSound()
                        animateCounterReset()
                    }
                }
            }
        }
    }

    private fun showCounterUI() {
        binding.counterGroup.visibility = android.view.View.VISIBLE
        binding.emptyStateContainer.visibility = android.view.View.GONE
        binding.rippleOverlay.isEnabled = true
    }

    private fun showEmptyState() {
        binding.counterGroup.visibility = android.view.View.GONE
        binding.emptyStateContainer.visibility = android.view.View.VISIBLE
        binding.rippleOverlay.isEnabled = false
    }

    private fun animateCounterIncrement() {
        val scaleX = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleX", 1f, 1.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleY", 1f, 1.1f, 1f)
        scaleX.duration = 100
        scaleY.duration = 100
        scaleX.interpolator = OvershootInterpolator()
        scaleY.interpolator = OvershootInterpolator()
        scaleX.start()
        scaleY.start()
    }

    private fun animateCounterPress() {
        // Scale down the entire container briefly
        val scaleX = ObjectAnimator.ofFloat(binding.counterFrame, "scaleX", 1f, 0.98f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.counterFrame, "scaleY", 1f, 0.98f, 1f)
        scaleX.duration = 100
        scaleY.duration = 100
        scaleX.start()
        scaleY.start()
    }

    private fun animateCounterDecrement() {
        val scaleX = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleX", 1f, 0.9f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleY", 1f, 0.9f, 1f)
        scaleX.duration = 100
        scaleY.duration = 100
        scaleX.start()
        scaleY.start()
    }

    private fun animateCounterReset() {
        val rotation = ObjectAnimator.ofFloat(binding.tvCurrentCount, "rotation", 0f, -360f)
        rotation.duration = 400
        rotation.interpolator = AccelerateDecelerateInterpolator()
        rotation.start()
    }

    private fun showResetDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reset_counter_title))
            .setMessage(getString(R.string.reset_counter_message))
            .setPositiveButton(getString(R.string.reset)) { _, _ ->
                viewModel.resetCount()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}
