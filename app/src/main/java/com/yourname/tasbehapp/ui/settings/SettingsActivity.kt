package com.yourname.tasbehapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yourname.tasbehapp.databinding.ActivitySettingsBinding
import com.yourname.tasbehapp.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.utils.HapticManager
import com.yourname.tasbehapp.utils.SoundManager

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    
    @Inject
    lateinit var preferenceManager: PreferenceManager
    
    @Inject
    lateinit var hapticManager: HapticManager
    
    @Inject
    lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            // Apply padding to AppBarLayout to ensure scrolling content sits below it properly
            binding.appBarLayout.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        setupToolbar()
        loadPreferences()
        setupListeners()
    }

    private fun setupEdgeToEdge() {
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        androidx.core.view.WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadPreferences() {
        binding.switchSound.isChecked = preferenceManager.isSoundEnabled
        binding.switchHaptic.isChecked = preferenceManager.isHapticEnabled
        binding.switchKeepScreenOn.isChecked = preferenceManager.keepScreenOn
        binding.tvCurrentTheme.text = preferenceManager.theme
        
        binding.llHapticIntensity.visibility = if (preferenceManager.isHapticEnabled) android.view.View.VISIBLE else android.view.View.GONE
        binding.sliderHapticIntensity.value = preferenceManager.hapticIntensity.toFloat()

        // Per-sound volume initialization & migration
        preferenceManager.migrateVolumeSettings()
        
        val clickVol = preferenceManager.clickVolume
        val clickPercent = (clickVol * 100).toInt()
        binding.sliderClickVolume.value = clickPercent.toFloat()
        binding.textClickVolumeValue.text = getString(R.string.percentage_format, clickPercent)
        soundManager.setClickVolume(clickVol)

        val successVol = preferenceManager.successVolume
        val successPercent = (successVol * 100).toInt()
        binding.sliderSuccessVolume.value = successPercent.toFloat()
        binding.textSuccessVolumeValue.text = getString(R.string.percentage_format, successPercent)
        soundManager.setSuccessVolume(successVol)

        val resetVol = preferenceManager.resetVolume
        val resetPercent = (resetVol * 100).toInt()
        binding.sliderResetVolume.value = resetPercent.toFloat()
        binding.textResetVolumeValue.text = getString(R.string.percentage_format, resetPercent)
        soundManager.setResetVolume(resetVol)

        binding.llSoundVolumes.visibility = if (preferenceManager.isSoundEnabled) android.view.View.VISIBLE else android.view.View.GONE
        
        // Use packageManager to get version name if possible, or just hardcode for now
        val versionName = try {
            packageManager.getPackageInfo(packageName, 0).versionName
        } catch (_: Exception) {
            "1.0.0"
        }
        binding.tvVersion.text = getString(R.string.app_version, versionName)
    }

    private fun setupListeners() {
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            preferenceManager.isSoundEnabled = isChecked
            binding.llSoundVolumes.visibility = if (isChecked) android.view.View.VISIBLE else android.view.View.GONE
        }

        binding.switchHaptic.setOnCheckedChangeListener { _, isChecked ->
            preferenceManager.isHapticEnabled = isChecked
            binding.llHapticIntensity.visibility = if (isChecked) android.view.View.VISIBLE else android.view.View.GONE
        }

        binding.sliderHapticIntensity.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val intensityValue = value.toInt()
                hapticManager.setIntensityWithPreview(intensityValue)
                preferenceManager.hapticIntensity = intensityValue
            }
        }

        binding.sliderClickVolume.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val volumePercentage = value.toInt()
                val volumeFloat = value / 100f
                binding.textClickVolumeValue.text = getString(R.string.percentage_format, volumePercentage)
                soundManager.setClickVolume(volumeFloat)
                soundManager.playClickSound()
                preferenceManager.clickVolume = volumeFloat
            }
        }

        binding.sliderSuccessVolume.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val volumePercentage = value.toInt()
                val volumeFloat = value / 100f
                binding.textSuccessVolumeValue.text = getString(R.string.percentage_format, volumePercentage)
                soundManager.setSuccessVolume(volumeFloat)
                soundManager.playTargetReachedSound()
                preferenceManager.successVolume = volumeFloat
            }
        }

        binding.sliderResetVolume.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val volumePercentage = value.toInt()
                val volumeFloat = value / 100f
                binding.textResetVolumeValue.text = getString(R.string.percentage_format, volumePercentage)
                soundManager.setResetVolume(volumeFloat)
                soundManager.playResetSound()
                preferenceManager.resetVolume = volumeFloat
            }
        }

        binding.switchKeepScreenOn.setOnCheckedChangeListener { _, isChecked ->
            preferenceManager.keepScreenOn = isChecked
        }

        binding.rlTheme.setOnClickListener {
            // TODO: Implement Theme selection dialog
        }

        binding.btnDebugSound.setOnClickListener {
            val intent = android.content.Intent(this, SoundTestActivity::class.java)
            startActivity(intent)
        }
    }
}
